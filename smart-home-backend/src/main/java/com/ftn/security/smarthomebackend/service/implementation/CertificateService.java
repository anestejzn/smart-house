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
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
    public void createAndSaveLeafCertificate(NewCertificateRequest certReq)
            throws EntityNotFoundException, AliasAlreadyExistsException, KeyStoreCertificateException, InvalidKeyUsagesComboException, CertificateParsingException, CertificateEncodingException, KeyStoreException, MailCannotBeSentException, MessagingException, IOException, KeyStoreMalfunctionedException, AliasDoesNotExistException {
        CSR csr = csrService.getById(certReq.getCsrId());

        if (keyStoreService.containsAlias(csr.getUser().getEmail()))
            throw new AliasAlreadyExistsException("Alias already exists!");

        IssuerData issuerData = keyStoreService.getIssuerBySubjAlias(certReq.getIntermediateAlias());
        X509Certificate cert = (X509Certificate) keyStoreService.getCertificateByAlias(certReq.getIntermediateAlias());
        KeyPair keyPairIssuer = new KeyPair(cert.getPublicKey(), issuerData.getPrivateKey());

        KeyPair keyPairSubject = keyStoreService.generateKeyPair();
        SubjectData subjectData = generateSubjectDataFromX500Name(
                generateX500Name(csr),
                keyPairSubject,
                LocalDate.now().toString(),
                LocalDate.now().plusMonths(Long.parseLong(certReq.getValidityPeriod())).toString(),
                keyStoreService.generateNextSerialNumber().toString()
        );

        assert subjectData != null;
        X509Certificate subjectCert = createNewLeafCertificate(subjectData, issuerData, keyPairIssuer.getPublic(), certReq);

        keyStoreService.loadKeyStore();
        String alias = csr.getUser().getEmail();
        keyStoreService.saveCertificate(alias, keyPairSubject.getPrivate(), alias.toCharArray(), subjectCert);
        keyStoreService.saveKeyStore();

        User user = csr.getUser();
        user.setStatus(AccountStatus.ACTIVE);
        userService.save(user);
        webSocketService.sendMessageAboutCreateCertificate(user.getEmail());
        sendEmailWithCertificate(user.getEmail());
        csrService.deleteById(csr.getId());
    }

    private void sendEmailWithCertificate(String email) throws CertificateParsingException, CertificateEncodingException, KeyStoreException, MailCannotBeSentException, KeyStoreCertificateException, IOException, MessagingException, KeyStoreMalfunctionedException {
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

    @Override
    public List<SortedAliasesResponse> getAliases(CertificateSortType type, CertificateValidityType validity)
            throws KeyStoreCertificateException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificateException, KeyStoreMalfunctionedException {
        List<String> aliases = sortByType(type);
        return sortByValidity(validity, aliases);
    }

    private List<SortedAliasesResponse> sortByValidity(CertificateValidityType validity, List<String> aliases) throws KeyStoreCertificateException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificateException, KeyStoreMalfunctionedException {
        boolean requiredValidity = validity == CertificateValidityType.VALID;

        List<SortedAliasesResponse> sortedAliases = new ArrayList<>();
        for (String alias : aliases)
            if (isCertificateChainValid(keyStoreService.getCertificateChainByAlias(alias)) == requiredValidity)
                sortedAliases.add(new SortedAliasesResponse(alias, requiredValidity));
        return sortedAliases;
    }

    private List<String> sortByType(CertificateSortType type) throws KeyStoreCertificateException, KeyStoreMalfunctionedException {
        List<String> allAliases = keyStoreService.getAllAliases();
        switch (type) {
            case ALL -> {
                return allAliases;
            }
            case ROOT -> {
                return allAliases.stream().filter(name -> name.equalsIgnoreCase("root")).toList();
            }
            case INTERMEDIATE -> {
                return allAliases.stream().filter(
                                    name -> name.equalsIgnoreCase("intermediate1") ||
                                            name.equalsIgnoreCase("intermediate2")
                            ).toList();
            }
            default -> {
                return allAliases.stream().filter(
                        name -> !name.equalsIgnoreCase("intermediate1") &&
                                !name.equalsIgnoreCase("intermediate2") &&
                                !name.equalsIgnoreCase("root")
                ).toList();
            }
        }
    }

    @Override
    public List<CertificateResponse> getCertificateByAlias(String alias) throws KeyStoreCertificateException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificateException, KeyStoreMalfunctionedException {
        List<X509Certificate> certificates = keyStoreService.getCertificateChainByAlias(alias);
        isCertificateChainValid(certificates);
        return certificates.stream().map((c) -> new CertificateResponse(c, true)).toList();
    }

    public boolean cancelCertificate(String alias, String reason) throws EntityNotFoundException, AliasDoesNotExistException, KeyStoreMalfunctionedException {
        if (!keyStoreService.containsAlias(alias))
            throw new EntityNotFoundException(alias, EntityType.CERTIFICATE);

        cancelCertificateService.cancelCertificate(alias, reason);
        return true;
    }

    @Override
    public void createAndSaveRootCertificate() throws KeyStoreMalfunctionedException {
        KeyPair keyPairRoot = keyStoreService.generateKeyPair();
        X500Name x500Name = generateRootX500Name();
        IssuerData issuerData = new IssuerData(x500Name, keyPairRoot.getPrivate());
        SubjectData subjectData = generateSubjectDataFromX500Name(
                x500Name, keyPairRoot, "2023-03-25", "2027-12-31", "1"
        );

        Certificate cert = createNewCertificate(subjectData, issuerData, keyPairRoot.getPublic());

        keyStoreService.createNewKeyStore();
        keyStoreService.saveCertificate(ROOT_CERT_ALIAS, issuerData.getPrivateKey(), ROOT_CERT_ALIAS.toCharArray(), cert);
        keyStoreService.saveKeyStore();
    }

    @Override
    public void createAndSaveIntermediateCertificate(final String alias) throws KeyStoreMalfunctionedException, AliasDoesNotExistException {
        IssuerData issuerData = keyStoreService.getIssuerBySubjAlias(ROOT_CERT_ALIAS);
        X509Certificate cert = (X509Certificate) keyStoreService.getCertificateByAlias(ROOT_CERT_ALIAS);
        KeyPair keyPairIssuer = new KeyPair(cert.getPublicKey(), issuerData.getPrivateKey());

        KeyPair keyPairSubject = keyStoreService.generateKeyPair();
        X500Name x500NameSubject = generateIntermediateX500Name(alias);
        SubjectData subjectData = generateSubjectDataFromX500Name(x500NameSubject, keyPairSubject, "2023-03-25", "2025-12-31", "2");
        X509Certificate subjectCert = createNewCertificate(subjectData, issuerData, keyPairIssuer.getPublic());

        try {
            subjectCert.verify(keyPairIssuer.getPublic());
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException |
                 SignatureException e) {
            e.printStackTrace();
        }

        keyStoreService.loadKeyStore();
        keyStoreService.saveCertificate(alias, keyPairSubject.getPrivate(), alias.toCharArray(), subjectCert);
        keyStoreService.saveKeyStore();
    }

    private String getAliasFromCertificate(X509Certificate cert) {
        return String.valueOf(
                new X500Name(cert.getSubjectX500Principal().getName())
                        .getRDNs(BCStyle.UID)[0].getTypesAndValues()[0].getValue()
        );
    }

    private boolean isCertificateChainValid(List<X509Certificate> certChain) throws InvalidCertificateException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        X509Certificate leftToVerify = null;
        for (X509Certificate cert : certChain) {
            String certAlias = getAliasFromCertificate(cert);

            if (certAlias.equals(ROOT_CERT_ALIAS))
                cert.verify(cert.getPublicKey());
            else if (leftToVerify != null)
                leftToVerify.verify(cert.getPublicKey());

            if (cancelCertificateService.mostRecentCancelledAliasExists(certAlias))
                return false;

            if (cert.getNotAfter().before(new Date()))
                return false;

            leftToVerify = cert;
        }
        return true;
    }

    private X509Certificate createNewLeafCertificate(SubjectData subjectData, IssuerData issuerData,
                                                     PublicKey issuerPublicKey, NewCertificateRequest certificateReq) throws InvalidKeyUsagesComboException {
        X509Certificate cert = null;
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                    issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey()
            );

            CertificateUtils.validateKeyUsagesSelection(certificateReq.getKeyUsages());
            addExtensions(certGen, certificateReq, subjectData.getPublicKey(), issuerPublicKey);
            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            cert = certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException |
                 CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }

    private void addExtensions(X509v3CertificateBuilder certGen, NewCertificateRequest certificateRequest, PublicKey subjectPublicKey, PublicKey issuerPublicKey) {
        try {
            JcaX509ExtensionUtils certExtUtils = new JcaX509ExtensionUtils();
            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
            certGen.addExtension(Extension.authorityKeyIdentifier, false, certExtUtils.createAuthorityKeyIdentifier(issuerPublicKey));
            certGen.addExtension(Extension.subjectKeyIdentifier, false, certExtUtils.createSubjectKeyIdentifier(subjectPublicKey));
            certGen.addExtension(
                    Extension.keyUsage, false,
                    new KeyUsage(
                            Arrays.stream(certificateRequest.getKeyUsages())
                                    .map(CertificateUtils::mapKeyUsageStrToInt)
                                    .reduce(0, (a, b) -> a | b)
                    )
            );
            certGen.addExtension(
                    Extension.extendedKeyUsage, false,
                    new ExtendedKeyUsage(
                            Arrays.stream(certificateRequest.getExtendedKeyUsages())
                                    .map(CertificateUtils::mapExtendedKeyUsageStrToKeyPurposeId)
                                    .filter(Objects::nonNull).toArray(KeyPurposeId[]::new)
                    )
            );
        } catch (NoSuchAlgorithmException | CertIOException e) {
            e.printStackTrace();
        }
    }

    private X509Certificate createNewCertificate(SubjectData subjectData, IssuerData issuerData, PublicKey issuerPublicKey) {
        X509Certificate cert = generateCertificate(subjectData, issuerData, issuerPublicKey);
        assert cert != null;
        try {
            cert.verify(issuerPublicKey);
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException |
                 SignatureException e) {
            e.printStackTrace();
        }
        return cert;
    }

    private X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, PublicKey issuerPublicKey) {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");
            System.out.println(builder);
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            JcaX509ExtensionUtils certExtUtils = new JcaX509ExtensionUtils();
            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
            certGen.addExtension(Extension.authorityKeyIdentifier, false, certExtUtils.createAuthorityKeyIdentifier(issuerPublicKey));
            certGen.addExtension(Extension.subjectKeyIdentifier, false, certExtUtils.createSubjectKeyIdentifier(subjectData.getPublicKey()));

            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            return certConverter.getCertificate(certHolder);
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException |
                 CertIOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X500Name generateRootX500Name() {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "SmartHome");
        builder.addRDN(BCStyle.O, "SmartHomeCert");
        builder.addRDN(BCStyle.OU, "IT Department");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "root@maildrop.cc");
        builder.addRDN(BCStyle.UID, "root");
        return builder.build();
    }

    private X500Name generateX500Name(CSR csrData) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.E, csrData.getUser().getEmail());
        builder.addRDN(BCStyle.CN, csrData.getCommonName());
        builder.addRDN(BCStyle.O, csrData.getOrganization());
        builder.addRDN(BCStyle.OU, csrData.getOrganizationUnit());
        builder.addRDN(BCStyle.ST, csrData.getState());
        builder.addRDN(BCStyle.C, csrData.getCountry());
        builder.addRDN(BCStyle.UID, csrData.getUser().getEmail());
        return builder.build();
    }

    private X500Name generateIntermediateX500Name(String interAlias) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Admin");
        builder.addRDN(BCStyle.O, "SmartHomeCert");
        builder.addRDN(BCStyle.OU, "IT Department");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, interAlias + "@maildrop.cc");
        builder.addRDN(BCStyle.UID, interAlias);
        return builder.build();
    }

    private SubjectData generateSubjectDataFromX500Name(X500Name x500Name, KeyPair keyPairSubject, String startDate, String endDate, String sn) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return new SubjectData(keyPairSubject.getPublic(), x500Name, sn, df.parse(startDate), df.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
