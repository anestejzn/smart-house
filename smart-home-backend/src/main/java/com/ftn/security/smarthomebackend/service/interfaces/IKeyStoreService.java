package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.model.IssuerData;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public interface IKeyStoreService {
    void createNewKeyStore();
    KeyPair generateKeyPair();
    void loadKeyStore();
    void saveKeyStore();
    void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate);
    IssuerData readIssuerFromStore(String alias, char[] keyPass);
    Certificate readCertificate(String alias);
}
