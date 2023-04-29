package com.ftn.security.smarthomebackend.security;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class FingerprintUtils {
    public static String generateFingerprint(String rawFingerprint) {
        return new String(HMAC512(FingerprintProperties.SECRET).sign(rawFingerprint.getBytes()), StandardCharsets.UTF_8);
    }

    public static boolean verifyFingerprint(String fingerprint, String rawFingerprint) {
        return generateFingerprint(rawFingerprint).equals(fingerprint);
    }

    public static String generateRandomRawFingerprint() {
        return UUID.randomUUID().toString();
    }
}
