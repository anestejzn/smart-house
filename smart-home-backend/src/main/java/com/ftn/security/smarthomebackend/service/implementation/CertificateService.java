package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.model.IssuerData;
import com.ftn.security.smarthomebackend.model.SubjectData;
import com.ftn.security.smarthomebackend.service.interfaces.ICertificateService;
import com.ftn.security.smarthomebackend.service.interfaces.IKeyStoreService;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.asn1.x500.X500Name;

@Service

public class CertificateService implements ICertificateService {
    @Autowired
    private IKeyStoreService keyStoreService;


    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void createAndWriteRootCertificate() {
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
    public void createAndWriteIntermediateCertificate() {
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
        builder.addRDN(BCStyle.CN, "Developers");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "root@maildrop.cc");
        builder.addRDN(BCStyle.UID, "1");
        return builder.build();
    }

    private X500Name generateIntermediateX500Name() {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Admin");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "intermediate@maildrop.cc");
        builder.addRDN(BCStyle.UID, "2");
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
