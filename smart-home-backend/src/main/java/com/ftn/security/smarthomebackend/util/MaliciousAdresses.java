package com.ftn.security.smarthomebackend.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MaliciousAdresses {

    public static List<String> readMaliciousAddresses(){
        String filePath = "src/main/resources/malicious_addresses.txt";
        List<String> addresses = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addresses.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }
}
