package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;
import com.ftn.security.smarthomebackend.model.IssuerData;
import com.ftn.security.smarthomebackend.service.interfaces.IKeyStoreService;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.ftn.security.smarthomebackend.util.Constants.ERROR_WITH_CERTIFICATE_READING;

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
    public void write(String alias, PrivateKey privateKey, char[] aliasPass, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, aliasPass, new Certificate[]{certificate});
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
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(ksFilepath));
            keyStore.load(in, ksPassword);


            if (keyStore.isKeyEntry(alias)) {
                return keyStore.getCertificate(alias);
            }
        } catch (KeyStoreException | NoSuchAlgorithmException |
                 CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean containsAlias(String alias) {
        try {
            keyStore.load(new BufferedInputStream(new FileInputStream(ksFilepath)), ksPassword);
            return keyStore.containsAlias(alias);
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Long generateNextSerialNumber() throws KeyStoreCertificateException {
        Enumeration<String> en = loadAliases();
        List<String> temp = new ArrayList<>();

        while(en.hasMoreElements())
            temp.add(en.nextElement());

        return (long) (temp.size() + 1);
    }

    @Override
    public List<String> getAliases() throws KeyStoreCertificateException {
        Enumeration<String> currentAliases = loadAliases();
        List<String> aliases = new ArrayList<>();

        while(currentAliases.hasMoreElements())
            aliases.add(currentAliases.nextElement());

        return aliases;
    }

    @Override
    public List<CertificateResponse> readCertificateChain(String alias)
            throws KeyStoreCertificateException
    {
        try {
            loadKeyStore();

            return extractCertificateChainByType(alias);
        } catch (KeyStoreException |
                CertificateException e) {
            throw new KeyStoreCertificateException(ERROR_WITH_CERTIFICATE_READING);
        }
    }

    private List<CertificateResponse> extractCertificateChainByType(String alias)
            throws KeyStoreException, CertificateParsingException {
        List<Certificate> listOfCertificates = new ArrayList<>();

        if (keyStore.isKeyEntry(alias)) {
            if (alias.equals("root")) {
                listOfCertificates.add(keyStore.getCertificate(alias));
            } else if (alias.equals("intermediate")) {
                listOfCertificates.add(keyStore.getCertificate("root"));
                listOfCertificates.add(keyStore.getCertificate(alias));
            } else {
                listOfCertificates.add(keyStore.getCertificate("root"));
                listOfCertificates.add(keyStore.getCertificate("intermediate"));
                listOfCertificates.add(keyStore.getCertificate(alias));
            }

            List<CertificateResponse> certs = new ArrayList<>();
            for (Certificate c : listOfCertificates)
                certs.add(new CertificateResponse((X509Certificate) c));

            return certs;
        }

        return null;
    }

    private Enumeration<String> loadAliases() throws KeyStoreCertificateException {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(ksFilepath));
            keyStore.load(in, ksPassword);

            return keyStore.aliases();
        } catch (KeyStoreException | NoSuchAlgorithmException |
                CertificateException | IOException e) {
            throw new KeyStoreCertificateException(ERROR_WITH_CERTIFICATE_READING);
        }
    }
}
