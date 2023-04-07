package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.exception.AliasDoesNotExistException;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;
import com.ftn.security.smarthomebackend.exception.KeyStoreMalfunctionedException;
import com.ftn.security.smarthomebackend.model.IssuerData;

import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.*;
import java.util.List;

public interface IKeyStoreService {
    void createNewKeyStore() throws KeyStoreMalfunctionedException;
    void loadKeyStore() throws KeyStoreMalfunctionedException;
    void saveKeyStore() throws KeyStoreMalfunctionedException;
    void saveCertificate(String alias, PrivateKey privateKey, char[] aliasPass, Certificate certificate) throws KeyStoreMalfunctionedException;
    KeyPair generateKeyPair() throws KeyStoreMalfunctionedException;
    PrivateKey getPrivateKeyByAlias(final String alias) throws KeyStoreMalfunctionedException;
    IssuerData getIssuerBySubjAlias(final String alias) throws KeyStoreMalfunctionedException;
    Certificate getCertificateByAlias(final String alias) throws KeyStoreMalfunctionedException, AliasDoesNotExistException;
    boolean containsAlias(final String alias) throws KeyStoreMalfunctionedException;
    Long generateNextSerialNumber() throws KeyStoreMalfunctionedException;
    List<String> getAllAliases() throws KeyStoreMalfunctionedException;
    List<X509Certificate> getCertificateChainByAlias(final String alias) throws KeyStoreCertificateException, KeyStoreMalfunctionedException;
    String generateKeyStoreRepresentationOfCertificate(final String alias) throws KeyStoreCertificateException, KeyStoreMalfunctionedException;
}
