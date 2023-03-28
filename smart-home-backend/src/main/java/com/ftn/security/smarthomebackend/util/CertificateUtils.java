package com.ftn.security.smarthomebackend.util;

import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;

import java.util.Map;

public class CertificateUtils {
    private static final Map<String, Integer> keyUsages = Map.of(
            "Digital Signature", KeyUsage.digitalSignature,
            "Non Repudiation", KeyUsage.nonRepudiation,
            "Key Encipherment", KeyUsage.keyEncipherment,
            "Data Encipherment", KeyUsage.dataEncipherment,
            "Key Agreement", KeyUsage.keyAgreement,
            "Certificate Signing", KeyUsage.keyCertSign,
            "CRL Signing", KeyUsage.cRLSign,
            "Encipher Only", KeyUsage.keyEncipherment,
            "Decipher Only", KeyUsage.decipherOnly
    );

    private static final Map<String, KeyPurposeId> extendedKeyUsages = Map.of(
            "Server Authentication", KeyPurposeId.id_kp_serverAuth,
            "Client Authentication", KeyPurposeId.id_kp_clientAuth,
            "Code Signing", KeyPurposeId.id_kp_codeSigning,
            "Email Protection", KeyPurposeId.id_kp_emailProtection,
            "Time Stamping", KeyPurposeId.id_kp_timeStamping
    );

    public static int mapKeyUsageStrToInt(String key) {
        return keyUsages.getOrDefault(key, 0);
    }

    public static KeyPurposeId mapExtendedKeyUsageStrToKeyPurposeId(String key) {
        return extendedKeyUsages.getOrDefault(key, null);
    }

}
