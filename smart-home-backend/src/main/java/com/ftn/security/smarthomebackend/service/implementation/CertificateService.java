package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.dto.response.SortedAliasesResponse;
import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.CertificateSortType;
import com.ftn.security.smarthomebackend.enums.CertificateValidityType;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.model.CSR;
import com.ftn.security.smarthomebackend.model.IssuerData;
import com.ftn.security.smarthomebackend.model.SubjectData;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.service.WebSocketService;
import com.ftn.security.smarthomebackend.service.interfaces.ICertificateService;
import com.ftn.security.smarthomebackend.service.interfaces.ICsrService;
import com.ftn.security.smarthomebackend.service.interfaces.IKeyStoreService;
import com.ftn.security.smarthomebackend.service.interfaces.IUserService;
import com.ftn.security.smarthomebackend.util.CertificateUtils;
import jakarta.mail.MessagingException;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.ftn.security.smarthomebackend.util.CertificateConstants.INTERMEDIATE_CERT_ALIASES;
import static com.ftn.security.smarthomebackend.util.CertificateConstants.ROOT_CERT_ALIAS;

@Service
public class CertificateService implements ICertificateService {
    @Autowired
    private IKeyStoreService keyStoreService;
    @Autowired
    private ICsrService csrService;
    @Autowired
    private IUserService userService;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private CancelCertificateService cancelCertificateService;
    @Autowired
    private EmailService emailService;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void createAndSaveLeafCertificate(final NewCertificateRequest certReq)
            throws EntityNotFoundException, AliasDoesNotExistException, KeyStoreMalfunctionedException, AliasAlreadyExistsException,
            InvalidCertificateException, KeyStoreCertificateException, CertificateParsingException, MessagingException, CertificateEncodingException,
            KeyStoreException, MailCannotBeSentException, IOException {
        CSR csr = csrService.getById(certReq.getCsrId());

        if (keyStoreService.containsAlias(csr.getUser().getEmail()))
            throw new AliasAlreadyExistsException("Alias already exists!");

        IssuerData issuer = keyStoreService.getIssuerBySubjAlias(certReq.getIntermediateAlias());
        KeyPair issuerKeyPair = new KeyPair(
                keyStoreService.getCertificateByAlias(certReq.getIntermediateAlias()).getPublicKey(),
                issuer.getPrivateKey()
        );

        KeyPair subjKeyPair = keyStoreService.generateKeyPair();
        SubjectData subj = generateSubjectDataFromX500Name(
                CertificateUtils.generateX500Name(
                        csr.getUser().getEmail(), csr.getCommonName(), csr.getOrganization(), csr.getOrganizationUnit(),
                        csr.getCountry(), csr.getState(), csr.getCity(), csr.getUser().getEmail()
                ),
                subjKeyPair,
                LocalDate.now().toString(),
                LocalDate.now().plusMonths(Long.parseLong(certReq.getValidityPeriod())).toString(),
                keyStoreService.generateNextSerialNumber().toString()
        );

        X509Certificate subjCert = CertificateUtils.generateX509Certificate(subj, issuer, issuerKeyPair.getPublic(), certReq);

        String alias = csr.getUser().getEmail();
        saveCertToKS(alias, subjKeyPair.getPrivate(), subjCert);

        User user = csr.getUser();
        user.setStatus(AccountStatus.ACTIVE);
        userService.save(user);
        webSocketService.sendMessageAboutCreateCertificate(user.getEmail());
        sendEmailWithCertificate(user.getEmail());
        csrService.deleteById(csr.getId());
    }

    @Override
    public void createAndSaveRootCertificate() throws KeyStoreMalfunctionedException, InvalidCertificateException, CertificateParsingException {
        KeyPair keyPairRoot = keyStoreService.generateKeyPair();
        X500Name x500Name = CertificateUtils.generateX500Name(
                "root@maildrop.cc", "SmartHome", "SmartHomeCert", "IT Department", "RS", "RS", "Belgrade", ROOT_CERT_ALIAS
        );
        IssuerData issuerData = new IssuerData(x500Name, keyPairRoot.getPrivate());
        SubjectData subjectData = generateSubjectDataFromX500Name(
                x500Name, keyPairRoot, "2023-03-25", "2027-12-31", "1"
        );

        X509Certificate cert = CertificateUtils.generateX509Certificate(subjectData, issuerData, keyPairRoot.getPublic(), null);
        saveCertToKS(ROOT_CERT_ALIAS, issuerData.getPrivateKey(), cert);
    }

    @Override
    public void createAndSaveIntermediateCertificate(final String alias) throws KeyStoreMalfunctionedException, AliasDoesNotExistException, InvalidCertificateException, CertificateParsingException {
        IssuerData issuerData = keyStoreService.getIssuerBySubjAlias(ROOT_CERT_ALIAS);
        X509Certificate cert = (X509Certificate) keyStoreService.getCertificateByAlias(ROOT_CERT_ALIAS);
        KeyPair keyPairIssuer = new KeyPair(cert.getPublicKey(), issuerData.getPrivateKey());

        KeyPair keyPairSubject = keyStoreService.generateKeyPair();
        X500Name x500NameSubject = CertificateUtils.generateX500Name(
                alias + "@maildrop.cc", "Admin", "SmartHomeCert", "IT Department", "RS", "RS", "Belgrade", alias
        );
        SubjectData subjectData = generateSubjectDataFromX500Name(x500NameSubject, keyPairSubject, "2023-03-25", "2025-12-31", "2");
        X509Certificate subjectCert = CertificateUtils.generateX509Certificate(subjectData, issuerData, keyPairIssuer.getPublic(), null);

        try {
            subjectCert.verify(keyPairIssuer.getPublic());
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException |
                 SignatureException e) {
            e.printStackTrace();
        }

        saveCertToKS(alias, keyPairSubject.getPrivate(), subjectCert);
    }

    @Override
    public List<CertificateResponse> getCertificateByAlias(final String alias) throws KeyStoreCertificateException, KeyStoreMalfunctionedException {
        List<X509Certificate> certificates = keyStoreService.getCertificateChainByAlias(alias);
        boolean isValid = isCertificateChainValid(certificates);
        return certificates.stream().map((c) -> new CertificateResponse(c, isValid)).toList();
    }

    @Override
    public boolean cancelCertificate(final String alias, final String reason) throws EntityNotFoundException, KeyStoreMalfunctionedException {
        if (!keyStoreService.containsAlias(alias))
            throw new EntityNotFoundException(alias, EntityType.CERTIFICATE);

        cancelCertificateService.cancelCertificate(alias, reason);
        return true;
    }

    @Override
    public List<SortedAliasesResponse> getAliasesByFilters(final CertificateSortType type, final CertificateValidityType validity) throws KeyStoreMalfunctionedException {
        boolean shouldBeValid = validity == CertificateValidityType.VALID;
        return filterByValidity(filterByType(type), shouldBeValid).stream()
                .map(alias -> new SortedAliasesResponse(alias, shouldBeValid)).toList();
    }

    private void saveCertToKS(String alias, PrivateKey privateKey, X509Certificate cert) throws KeyStoreMalfunctionedException {
        if (alias.equalsIgnoreCase(ROOT_CERT_ALIAS))
            keyStoreService.createNewKeyStore();
        keyStoreService.saveCertificate(alias, privateKey, alias.toCharArray(), cert);
        keyStoreService.saveKeyStore();
    }

    private void sendEmailWithCertificate(final String email) throws CertificateParsingException, CertificateEncodingException, KeyStoreException, MailCannotBeSentException, KeyStoreCertificateException, IOException, MessagingException, KeyStoreMalfunctionedException {
        String keyStoreRepresentation = keyStoreService.generateKeyStoreRepresentationOfCertificate(email);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write(keyStoreRepresentation);
        writer.flush();

        File file = File.createTempFile("temp", ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        outputStream.writeTo(fileOutputStream);
        emailService.sendEmailWithCertificate(file);
    }

    private List<String> filterByValidity(final List<String> aliases, final boolean validity) {
        return aliases.stream().filter(alias -> {
            try {
                return isCertificateChainValid(keyStoreService.getCertificateChainByAlias(alias)) == validity;
            } catch (KeyStoreCertificateException | KeyStoreMalfunctionedException e) {
                return false;
            }
        }).toList();
    }

    private List<String> filterByType(final CertificateSortType type) throws KeyStoreMalfunctionedException {
        List<String> allAliases = keyStoreService.getAllAliases();
        switch (type) {
            case ALL -> {
                return allAliases;
            }
            case ROOT -> {
                return allAliases.stream().filter(name -> name.equalsIgnoreCase(ROOT_CERT_ALIAS)).toList();
            }
            case INTERMEDIATE -> {
                return allAliases.stream().filter(name -> Arrays.asList(INTERMEDIATE_CERT_ALIASES).contains(name)).toList();
            }
            default -> {
                return allAliases.stream().filter(
                        name -> !Arrays.asList(INTERMEDIATE_CERT_ALIASES).contains(name) &&
                                !name.equalsIgnoreCase(ROOT_CERT_ALIAS)
                ).toList();
            }
        }
    }

    private String getAliasFromCertificate(final X509Certificate cert) {
        return String.valueOf(
                new X500Name(cert.getSubjectX500Principal().getName())
                        .getRDNs(BCStyle.UID)[0].getTypesAndValues()[0].getValue()
        );
    }

    private boolean isCertificateChainValid(final List<X509Certificate> certChain) {
        X509Certificate leftToVerify = null;
        for (X509Certificate cert : certChain) {
            String certAlias = getAliasFromCertificate(cert);

            try {
                if (certAlias.equals(ROOT_CERT_ALIAS))
                    cert.verify(cert.getPublicKey());
                else if (leftToVerify != null)
                    leftToVerify.verify(cert.getPublicKey());
            } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException |
                     SignatureException e) {
                return false;
            }

            if (cancelCertificateService.mostRecentCancelledAliasExists(certAlias) || cert.getNotAfter().before(new Date()))
                return false;

            leftToVerify = cert;
        }
        return true;
    }

    private SubjectData generateSubjectDataFromX500Name(
            final X500Name x500Name, final KeyPair keyPairSubject, final String startDate, final String endDate, final String sn
    ) throws CertificateParsingException {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return new SubjectData(keyPairSubject.getPublic(), x500Name, sn, df.parse(startDate), df.parse(endDate));
        } catch (ParseException e) {
            throw new CertificateParsingException();
        }
    }

}