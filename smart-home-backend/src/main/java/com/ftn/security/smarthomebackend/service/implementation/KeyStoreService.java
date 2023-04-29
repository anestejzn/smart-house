package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.exception.AliasDoesNotExistException;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;
import com.ftn.security.smarthomebackend.exception.KeyStoreMalfunctionedException;
import com.ftn.security.smarthomebackend.model.IssuerData;
import com.ftn.security.smarthomebackend.service.interfaces.IKeyStoreService;
import com.ftn.security.smarthomebackend.util.Constants;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;
import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;
import static com.ftn.security.smarthomebackend.util.exception_messages.KeyStoreExceptionMessages.*;

@Service
public class KeyStoreService implements IKeyStoreService {

    private KeyStore keyStore;
    private final static String KS_FILEPATH = Constants.KEYSTORE_FILEPATH;
    private final static char[] KS_PASSWORD = Constants.KEYSTORE_PASSWORD.toCharArray();

    @PostConstruct
    private void postConstruct() throws KeyStoreMalfunctionedException {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException | NoSuchProviderException ignored) {
            throw new KeyStoreMalfunctionedException(KS_CREATE_INSTANCE_FAILED);
        }
    }

    @Override
    public void createNewKeyStore() throws KeyStoreMalfunctionedException {
        try {
            keyStore.load(null, KS_PASSWORD);
            keyStore.store(new FileOutputStream(KS_FILEPATH), KS_PASSWORD);
        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException ignored) {
            throw new KeyStoreMalfunctionedException(KS_LOAD_FAILED);
        }
    }

    @Override
    public void loadKeyStore() throws KeyStoreMalfunctionedException {
        try {
            keyStore.load(new BufferedInputStream(new FileInputStream(KS_FILEPATH)), KS_PASSWORD);
        } catch (NoSuchAlgorithmException | CertificateException | IOException ignored) {
            throw new KeyStoreMalfunctionedException(KS_LOAD_FAILED);
        }
    }

    @Override
    public void saveKeyStore() throws KeyStoreMalfunctionedException {
        try {
            keyStore.store(new FileOutputStream(KS_FILEPATH), KS_PASSWORD);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException ignored) {
            throw new KeyStoreMalfunctionedException(KS_SAVE_FAILED);
        }
    }

    @Override
    public void saveCertificate(final String alias, final PrivateKey privateKey, final char[] aliasPass, final Certificate certificate) throws KeyStoreMalfunctionedException {
        try {
            loadKeyStore();
            keyStore.setKeyEntry(alias, privateKey, aliasPass, new Certificate[]{certificate});
        } catch (KeyStoreException ignored) {
            throw new KeyStoreMalfunctionedException(KS_CERT_SAVE_FAILED);
        }
    }

    @Override
    public KeyPair generateKeyPair() throws KeyStoreMalfunctionedException {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstance("SHA1PRNG", "SUN"));
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException ignored) {
            throw new KeyStoreMalfunctionedException(KEY_PAIR_CREATION_FAILED);
        }
    }

    @Override
    public PrivateKey getPrivateKeyByAlias(final String alias) throws KeyStoreMalfunctionedException {
        try {
            loadKeyStore();
            return (PrivateKey) keyStore.getKey(alias, alias.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ignored) {
            throw new KeyStoreMalfunctionedException(KS_KEY_FETCH_FAILED);
        }
    }

    @Override
    public IssuerData getIssuerBySubjAlias(final String alias) throws KeyStoreMalfunctionedException {
        try {
            loadKeyStore();
            return new IssuerData(
                    new JcaX509CertificateHolder((X509Certificate) keyStore.getCertificate(alias)).getSubject(),
                    getPrivateKeyByAlias(alias)
            );
        } catch (KeyStoreException | CertificateException ignored) {
            throw new KeyStoreMalfunctionedException(KS_FETCH_ISSUER_FAILED);
        }
    }

    @Override
    public Certificate getCertificateByAlias(final String alias) throws KeyStoreMalfunctionedException, AliasDoesNotExistException {
        try {
            loadKeyStore();
            if (keyStore.isKeyEntry(alias))
                return keyStore.getCertificate(alias);
            else
                throw new AliasDoesNotExistException(ALIAS_DOES_NOT_EXIST);
        } catch (KeyStoreException ignored) {
            throw new KeyStoreMalfunctionedException(KS_FETCH_CERT_FAILED);
        }
    }

    @Override
    public boolean containsAlias(final String alias) throws KeyStoreMalfunctionedException {
        try {
            loadKeyStore();
            return keyStore.containsAlias(alias);
        } catch (KeyStoreException e) {
            throw new KeyStoreMalfunctionedException(KS_HAS_NOT_BEEN_LOADED);
        }
    }

    @Override
    public Long generateNextSerialNumber() throws KeyStoreMalfunctionedException {
        return getAllAliases().size() + 1L;
    }

    @Override
    public List<String> getAllAliases() throws KeyStoreMalfunctionedException {
        try {
            loadKeyStore();
            return Collections.list(keyStore.aliases());
        } catch (KeyStoreException e)  {
            throw new KeyStoreMalfunctionedException(KS_HAS_NOT_BEEN_LOADED);
        }
    }

    @Override
    public List<X509Certificate> getCertificateChainByAlias(final String alias) throws KeyStoreCertificateException, KeyStoreMalfunctionedException {
        List<X509Certificate> chain = new ArrayList<>();
        try {
            loadKeyStore();
            if (keyStore.isKeyEntry(alias)) {
                X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
                String certAlias = getAliasFromX500Name(cert.getSubjectX500Principal().getName());
                while (!certAlias.equalsIgnoreCase("root")) {
                    chain.add(cert);
                    certAlias = getAliasFromX500Name(cert.getIssuerX500Principal().getName());
                    cert = (X509Certificate) keyStore.getCertificate(certAlias);
                }
                chain.add(cert);
            }
        } catch (KeyStoreException e) {
            throw new KeyStoreCertificateException(ERROR_WITH_CERTIFICATE_READING);
        }
        return chain;
    }

    @Override
    public String generateKeyStoreRepresentationOfCertificate(final String alias) throws KeyStoreCertificateException, KeyStoreMalfunctionedException {
        try {
            loadKeyStore();
            Certificate certificate = keyStore.getCertificate(alias);

            if (certificate == null)
                throw new KeyStoreCertificateException(ALIAS_DOES_NOT_EXIST);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            KeyStore tempKeyStore = KeyStore.getInstance("JKS");
            tempKeyStore.load(null, null);
            tempKeyStore.setCertificateEntry("alias_name", certificate);
            tempKeyStore.store(baos, "temp_keystore_password".toCharArray());
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (KeyStoreException | CertificateException e) {
            throw new KeyStoreCertificateException(ERROR_WITH_CERTIFICATE_READING);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAliasFromX500Name(final String x500Name) {
        return String.valueOf(new X500Name(x500Name).getRDNs(BCStyle.UID)[0].getTypesAndValues()[0].getValue());
    }

}