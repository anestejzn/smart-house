package com.ftn.security.smarthomebackend.service.interfaces;

import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class SymmetricAlgorithmService implements ISymmetricAlgorithmService{
    private static final String key = "*ASS12345ass!=56789*ASS";
    private static final String iv = "1234567890abcdef";
    @Override
    public String decryptMessage(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] ciphertextBytes = Base64.getDecoder().decode(message);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
