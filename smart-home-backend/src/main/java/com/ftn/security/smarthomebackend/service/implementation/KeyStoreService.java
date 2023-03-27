package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.model.IssuerData;
import com.ftn.security.smarthomebackend.service.interfaces.IKeyStoreService;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class KeyStoreService implements IKeyStoreService {
    private KeyStore keyStore;
    public final static String ksFilepath = "src/main/resources/keystore/keystore.jks";
    public final static char[] ksPassword = "kspassword".toCharArray();

    @PostConstruct
    private void postConstruct() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNewKeyStore() {
        try {
            keyStore.load(null, ksPassword);
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadKeyStore() {
        try {
            keyStore.load(new FileInputStream(ksFilepath), ksPassword);
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveKeyStore() {
        try {
            keyStore.store(new FileOutputStream(ksFilepath), ksPassword);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IssuerData readIssuerFromStore(String alias, char[] keyPass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(ksFilepath));
            keyStore.load(in, ksPassword);

            Certificate cert = keyStore.getCertificate(alias);

            PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
            return new IssuerData(issuerName, privKey);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException |
                 UnrecoverableKeyException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Certificate readCertificate(String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(ksFilepath));
            ks.load(in, ksPassword);


            if (ks.isKeyEntry(alias)) {
                return ks.getCertificate(alias);
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException |
                 CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
