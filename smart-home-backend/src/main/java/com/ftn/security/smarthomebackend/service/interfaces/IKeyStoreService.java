package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;
import com.ftn.security.smarthomebackend.model.IssuerData;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.List;

public interface IKeyStoreService {
    void createNewKeyStore();
    KeyPair generateKeyPair();
    void loadKeyStore();
    void saveKeyStore();
    void write(String alias, PrivateKey privateKey, char[] aliasPass, Certificate certificate);
    IssuerData readIssuerFromStore(String alias, char[] keyPass);
    Certificate readCertificate(String alias);
    boolean containsAlias(String alias);
    Long generateNextSerialNumber() throws KeyStoreCertificateException;
    List<String> getAliases() throws KeyStoreCertificateException;
    List<CertificateResponse> readCertificateChain(String alias) throws KeyStoreCertificateException;
    String generateKeyStoreRepresentationOfCertificate(String alias) throws CertificateParsingException, KeyStoreException, CertificateEncodingException, KeyStoreCertificateException;
}
