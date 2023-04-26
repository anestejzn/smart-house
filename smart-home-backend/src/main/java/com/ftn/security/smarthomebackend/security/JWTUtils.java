package com.ftn.security.smarthomebackend.security;

import com.auth0.jwt.JWT;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTUtils {
    public static String generateJWT(String email, String rawFingerPrint) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim(FingerprintProperties.FINGERPRINT_CLAIM, FingerprintUtils.generateFingerprint(rawFingerPrint))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
    }
}
