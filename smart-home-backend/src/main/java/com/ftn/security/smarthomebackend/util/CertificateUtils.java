package com.ftn.security.smarthomebackend.util;

import com.ftn.security.smarthomebackend.exception.InvalidKeyUsagesComboException;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import java.util.Map;

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

    public static void validateKeyUsagesSelection(final String[] keyUsages) throws InvalidKeyUsagesComboException {
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
}
