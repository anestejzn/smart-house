package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.dto.response.SortedAliasesResponse;
import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.CertificateSortType;
import com.ftn.security.smarthomebackend.enums.CertificateValidityType;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.AliasAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.InvalidKeyUsagesComboException;
import com.ftn.security.smarthomebackend.exception.InvalidCertificateException;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;
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
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
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

import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void createAndSaveLeafCertificate(NewCertificateRequest certificateRequest)
            throws EntityNotFoundException, AliasAlreadyExistsException, KeyStoreCertificateException, InvalidKeyUsagesComboException {
        CSR csr = csrService.getById(certificateRequest.getCsrId());

        if (keyStoreService.containsAlias(csr.getUser().getEmail()))
            throw new AliasAlreadyExistsException("Alias already exists!");

        IssuerData issuerData = keyStoreService.readIssuerFromStore("intermediate", "intermediate".toCharArray());
        X509Certificate cert = (X509Certificate) keyStoreService.readCertificate("intermediate");
        KeyPair keyPairIssuer = new KeyPair(cert.getPublicKey(), issuerData.getPrivateKey());

        KeyPair keyPairSubject = keyStoreService.generateKeyPair();
        X500Name x500NameSubject = generateX500Name(csr);
        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().plusYears(1).toString();
        String sn = keyStoreService.generateNextSerialNumber().toString();
        SubjectData subjectData = generateSubjectDataFromX500Name(x500NameSubject, keyPairSubject, startDate, endDate, sn);

        assert subjectData != null;
        X509Certificate subjectCert = createNewLeafCertificate(subjectData, issuerData, keyPairIssuer.getPublic(), certificateRequest);

        keyStoreService.loadKeyStore();
        String alias = csr.getUser().getEmail();
        keyStoreService.write(alias, keyPairSubject.getPrivate(), alias.toCharArray(), subjectCert);
        keyStoreService.saveKeyStore();

        User user = csr.getUser();
        user.setStatus(AccountStatus.ACTIVE);
        userService.save(user);
        webSocketService.sendMessageAboutCreateCertificate(user.getEmail());

        csrService.deleteById(csr.getId());
    }

    @Override
    public List<SortedAliasesResponse> getAliases(CertificateSortType type, CertificateValidityType validity)
            throws KeyStoreCertificateException, EntityNotFoundException {
        List<String> aliases = sortByType(type);

        return sortByValidity(validity, aliases);
    }

    private List<SortedAliasesResponse> sortByValidity(CertificateValidityType validity, List<String> aliases)
            throws EntityNotFoundException
    {
        List<SortedAliasesResponse> sortedAliases = new ArrayList<>();
        boolean valid = validity == CertificateValidityType.VALID;

        for (String alias : aliases) {
           if (validateCertificate(alias) == valid) {
               sortedAliases.add(new SortedAliasesResponse(alias, valid));
           }
        }

        return sortedAliases;
    }

    private List<String> sortByType(CertificateSortType type) throws KeyStoreCertificateException {
        List<String> allAliases = keyStoreService.getAliases();
        switch (type) {
            case ALL -> {
                return allAliases;
            }
            case ROOT -> {
                return allAliases.stream().filter(name -> name.equalsIgnoreCase("root")).toList();
            }
            case INTERMEDIATE -> {
                return allAliases.stream().filter(name -> name.equalsIgnoreCase("intermediate")).toList();
            }
            default -> {
                return allAliases.stream().filter(name -> !(name.equalsIgnoreCase("intermediate") || name.equalsIgnoreCase("root"))).toList();
            }
        }
    }

    @Override
    public List<CertificateResponse> getCertificateByAlias(String alias)
            throws KeyStoreCertificateException, EntityNotFoundException
    {
        List<CertificateResponse> certificates = keyStoreService.readCertificateChain(alias);
        for (CertificateResponse cr : certificates) {
            cr.setValid(validateCertificate(cr.getAlias()));
        }

        return certificates;
    }

    public boolean cancelCertificate(String alias, String reason) throws EntityNotFoundException, AliasAlreadyExistsException {
        if(!keyStoreService.containsAlias(alias)){
            throw new EntityNotFoundException(alias, EntityType.CERTIFICATE);
        }

        cancelCertificateService.cancelCertificate(alias, reason);

        return true;
    }

    @Override
    public void createAndSaveRootCertificate() {
        KeyPair keyPairRoot = keyStoreService.generateKeyPair();
        X500Name x500Name = generateRootX500Name();
        IssuerData issuerData =  new IssuerData(x500Name, keyPairRoot.getPrivate());
        SubjectData subjectData = generateSubjectDataFromX500Name(
                x500Name, keyPairRoot, "2023-03-25", "2027-12-31", "1"
        );

        Certificate cert = createNewCertificate(subjectData, issuerData, keyPairRoot.getPublic());

        keyStoreService.createNewKeyStore();
        keyStoreService.write("root", issuerData.getPrivateKey(), "root".toCharArray(), cert);
        keyStoreService.saveKeyStore();
    }

    @Override
    public void createAndSaveIntermediateCertificate() {
        IssuerData issuerData = keyStoreService.readIssuerFromStore("root", "root".toCharArray());
        X509Certificate cert = (X509Certificate) keyStoreService.readCertificate("root");
        KeyPair keyPairIssuer = new KeyPair(cert.getPublicKey(), issuerData.getPrivateKey());

        KeyPair keyPairSubject = keyStoreService.generateKeyPair();
        X500Name x500NameSubject = generateIntermediateX500Name();
        SubjectData subjectData = generateSubjectDataFromX500Name(x500NameSubject, keyPairSubject, "2023-03-25", "2025-12-31", "2");
        X509Certificate subjectCert = createNewCertificate(subjectData, issuerData, keyPairIssuer.getPublic());

        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(subjectCert.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(subjectCert.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(subjectCert);
        System.out.println("-------------------------------------------------------");

        try {
            subjectCert.verify(keyPairIssuer.getPublic());
            System.out.println("\nValidacija uspesna :)");
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
            System.out.println("\nValidacija neuspesna :(");
            e.printStackTrace();
        }

        keyStoreService.loadKeyStore();
        keyStoreService.write("intermediate", keyPairSubject.getPrivate(), "intermediate".toCharArray(), subjectCert);
        keyStoreService.saveKeyStore();
    }

    @Override
    public boolean validateCertificate(String alias) throws EntityNotFoundException
    {
        if (!keyStoreService.containsAlias(alias)) throw new EntityNotFoundException(alias, EntityType.CERTIFICATE);

        try {
            if(alias.equals("root")) validateRootCertificate(alias);
            else if(alias.equals("intermediate")) validateIntermediateCertificate(alias);
            else validateLeafCertificate(alias);
        } catch (CertificateException | NoSuchAlgorithmException | SignatureException |
                InvalidKeyException | NoSuchProviderException | InvalidCertificateException e) {
            return false;
        }

        return true;
    }

    private void validateLeafCertificate(String alias)
            throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, EntityNotFoundException, InvalidCertificateException
    {
        validateRootCertificate("root");
        PublicKey intermediatePKey = validateIntermediateCertificate("intermediate");
        X509Certificate leafCert = (X509Certificate) keyStoreService.readCertificate(alias);
        validateCertificate(leafCert, intermediatePKey, alias);
    }

    private PublicKey validateIntermediateCertificate(String alias)
            throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, EntityNotFoundException, InvalidCertificateException
    {
        PublicKey rootPKey = validateRootCertificate("root");
        X509Certificate intermediateCert = (X509Certificate) keyStoreService.readCertificate(alias);
        validateCertificate(intermediateCert, rootPKey, alias);
        return intermediateCert.getPublicKey();
    }

    private PublicKey validateRootCertificate(String alias)
            throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, EntityNotFoundException, InvalidCertificateException
    {
        X509Certificate rootCert = (X509Certificate) keyStoreService.readCertificate(alias);
        validateCertificate(rootCert, rootCert.getPublicKey(), alias);
        return rootCert.getPublicKey();
    }

    private void validateCertificate(X509Certificate certificate, PublicKey publicKey, String alias)
            throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, EntityNotFoundException, InvalidCertificateException
    {
        if(cancelCertificateService.mostRecentCancelledAliasExists(alias))
            throw new InvalidCertificateException(alias);

        LocalDate validFrom = certificate.getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate validTo = certificate.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        if(now.isBefore(validFrom) || now.isAfter(validTo))
            throw new InvalidCertificateException(alias);

        certificate.verify(publicKey);
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
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException e) {
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

        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        assert cert != null;
        System.out.println(cert.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(cert.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(cert);
        System.out.println("-------------------------------------------------------");

        try {
            cert.verify(issuerPublicKey);
            System.out.println("\nValidacija uspesna :)");
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
            System.out.println("\nValidacija neuspesna :(");
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

    private X500Name generateIntermediateX500Name() {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Admin");
        builder.addRDN(BCStyle.O, "SmartHomeCert");
        builder.addRDN(BCStyle.OU, "IT Department");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "intermediate@maildrop.cc");
        builder.addRDN(BCStyle.UID, "intermediate");
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
