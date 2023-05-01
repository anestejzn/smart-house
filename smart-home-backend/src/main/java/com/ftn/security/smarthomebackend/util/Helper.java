package com.ftn.security.smarthomebackend.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ftn.security.smarthomebackend.util.Constants.*;

public class Helper {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    public static boolean passwordsDontMatch(String password, String confirmationPassword){

        return !password.equals(confirmationPassword);
    }

    public static String getHash(String password) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static int generateSecurityCode() {
        return (int)(Math.random() * (Constants.MAX_SECURITY_NUM - Constants.MIN_SECURITY_NUM + 1) + Constants.MIN_SECURITY_NUM);
    }

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static String generateHashForURL(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String base64Hash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return base64Hash;
        } catch (NoSuchAlgorithmException e) {
            // Handle exception
            return null;
        }
    }

    public static List<Integer> extractSqMetersNumbers(String input) {
        try {
            String[] extract = input.split(":");
            List<Integer> nums = new LinkedList<>(); 
            nums.add(Integer.parseInt(extract[0]));
            nums.add(Integer.parseInt(extract[1]));
            
            return nums;
        } catch (Exception e) {
            
            return Arrays.asList(MIN_SQ_AREA, BOTTOM_MARGIN_FILTERING_SQ_AREA);
        }
    }

}
