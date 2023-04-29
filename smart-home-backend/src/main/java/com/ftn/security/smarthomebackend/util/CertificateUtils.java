package com.ftn.security.smarthomebackend.util;

import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.exception.InvalidCertificateException;
import com.ftn.security.smarthomebackend.exception.InvalidKeyUsagesComboException;
import com.ftn.security.smarthomebackend.model.IssuerData;
import com.ftn.security.smarthomebackend.model.SubjectData;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static com.ftn.security.smarthomebackend.util.exception_messages.CertificateServiceExceptionMessages.CANT_GEN_CERT;

public class CertificateUtils {
    private static final Map<String, Integer> keyUsagesStrToEnum = Map.of(
            "Digital Signature", KeyUsage.digitalSignature,
            "Non Repudiation", KeyUsage.nonRepudiation,
            "Key Encipherment", KeyUsage.keyEncipherment,
            "Data Encipherment", KeyUsage.dataEncipherment,
            "Key Agreement", KeyUsage.keyAgreement,
            "Certificate Signing", KeyUsage.keyCertSign,
            "CRL Signing", KeyUsage.cRLSign,
            "Encipher only", KeyUsage.encipherOnly,
            "Decipher only", KeyUsage.decipherOnly
    );

    private static final Map<String, KeyPurposeId> extendedKeyUsagesStrToEnum = Map.of(
            "Server Authentication", KeyPurposeId.id_kp_serverAuth,
            "Client Authentication", KeyPurposeId.id_kp_clientAuth,
            "Code Signing", KeyPurposeId.id_kp_codeSigning,
            "Email Protection", KeyPurposeId.id_kp_emailProtection,
            "Time Stamping", KeyPurposeId.id_kp_timeStamping
    );

    private static final Map<String, String> extendedKeyUsagesCodeToStr = Map.of(
            "1.3.6.1.5.5.7.3.1", "Server Authentication",
            "1.3.6.1.5.5.7.3.2", "Client Authentication",
            "1.3.6.1.5.5.7.3.3", "Code Signing",
            "1.3.6.1.5.5.7.3.4", "Email Protection",
            "1.3.6.1.5.5.7.3.8", "Time Stamping"
    );

    public static int mapKeyUsageStrToInt(final String key) {
        return keyUsagesStrToEnum.getOrDefault(key, 0);
    }

    public static KeyPurposeId mapExtendedKeyUsageStrToKeyPurposeId(final String key) {
        return extendedKeyUsagesStrToEnum.getOrDefault(key, null);
    }

    public static String mapExtendedKeyUsageCodeToStr(final String code) {
        return extendedKeyUsagesCodeToStr.getOrDefault(code, null);
    }

    public static X509Certificate generateX509Certificate(SubjectData subj, IssuerData issuer, PublicKey issuerPK, NewCertificateRequest certReq) throws InvalidCertificateException {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");
            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                    issuer.getX500name(),
                    new BigInteger(subj.getSerialNumber()),
                    subj.getStartDate(),
                    subj.getEndDate(),
                    subj.getX500name(),
                    subj.getPublicKey()
            );

            addRegularExtensions(certGen, subj.getPublicKey(), issuerPK);
            if (certReq != null) {
                CertificateUtils.validateKeyUsagesSelection(certReq.getKeyUsages());
                addExtensionsForLeaf(certGen, certReq);
            }
            else
                addExtensionsForRootOrInter(certGen);

            return new JcaX509CertificateConverter().setProvider("BC")
                    .getCertificate(certGen.build(builder.build(issuer.getPrivateKey())));
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException ignored ) {
            throw new InvalidCertificateException(CANT_GEN_CERT);
        } catch (InvalidKeyUsagesComboException e) {
            throw new InvalidCertificateException(e.getMessage());
        }
    }

    public static X500Name generateX500Name(final String bcE, final String bcCN, final String bcO, final String bcOU,
                                            final String bcC, final String bcST, final String bcL, final String bUID) {
        return new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.E, bcE).addRDN(BCStyle.CN, bcCN).addRDN(BCStyle.O, bcO).addRDN(BCStyle.OU, bcOU)
                .addRDN(BCStyle.C, bcC).addRDN(BCStyle.ST, bcST).addRDN(BCStyle.L, bcL).addRDN(BCStyle.UID, bUID)
                .build();
    }

    private static void validateKeyUsagesSelection(final String[] keyUsages) throws InvalidKeyUsagesComboException {
        boolean hasKeyAgreement = false;
        boolean hasDecipher = false;
        boolean hasEncipher = false;
        for (String kUsage : keyUsages)
            switch (kUsage) {
                case "Key Agreement" -> hasKeyAgreement = true;
                case "Encipher only" -> hasEncipher = true;
                case "Decipher only" -> hasDecipher = true;
            }

        if (hasDecipher && hasEncipher)
            throw new InvalidKeyUsagesComboException("'Decipher only' and 'Encipher only' key usages are mutually exclusive!");
        else if (!hasKeyAgreement && (hasDecipher || hasEncipher))
            throw new InvalidKeyUsagesComboException("'Key agreement' key usage must be selected if 'Decipher only' or 'Encipher only' is selected!");
    }

    private static void addRegularExtensions(X509v3CertificateBuilder certGen, PublicKey subjectPublicKey, PublicKey issuerPublicKey) {
        try {
            JcaX509ExtensionUtils certExtUtils = new JcaX509ExtensionUtils();
            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
            certGen.addExtension(Extension.authorityKeyIdentifier, false, certExtUtils.createAuthorityKeyIdentifier(issuerPublicKey));
            certGen.addExtension(Extension.subjectKeyIdentifier, false, certExtUtils.createSubjectKeyIdentifier(subjectPublicKey));
        } catch (NoSuchAlgorithmException | CertIOException ignored) {}
    }
    private static void addExtensionsForLeaf(X509v3CertificateBuilder certGen, NewCertificateRequest certificateRequest) {
        try {
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
        } catch (CertIOException ignored) {}
    }

    private static void addExtensionsForRootOrInter(X509v3CertificateBuilder certGen) {
        try {
            certGen.addExtension(
                    Extension.keyUsage, false,
                    new KeyUsage(keyUsagesStrToEnum.values().stream()
                            .filter(ku -> ku != KeyUsage.encipherOnly && ku != KeyUsage.decipherOnly)
                            .reduce(0, (a, b) -> a | b))
            );
            certGen.addExtension(
                    Extension.extendedKeyUsage, false,
                    new ExtendedKeyUsage(extendedKeyUsagesStrToEnum.values().toArray(new KeyPurposeId[0]))
            );
        } catch (CertIOException ignored) {}
    }


}