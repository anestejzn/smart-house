package com.ftn.security.smarthomebackend.util.exception_messages;

public class KeyStoreExceptionMessages {
    public static final String KS_UNKNOWN_FAIL = "Something wierd happened with key store and it exploded.";
    public static final String KS_HAS_NOT_BEEN_LOADED = "Key Store has not been loaded yet.";
    public static final String KS_CREATE_INSTANCE_FAILED = "Key Store instance creation failed, check given parameters.";
    public static final String KS_LOAD_FAILED = "Key Store failed to load, check given parameters.";
    public static final String KEY_PAIR_CREATION_FAILED = "Key pair creation failed, check if given algorithm.";
    public static final String KS_CERT_SAVE_FAILED = "Key store failed to save certificate!.";
    public static final String KS_SAVE_FAILED = "Key store failed to save its content into specified file.";
    public static final String KS_KEY_FETCH_FAILED = "Key store failed to fetch the private key.";
    public static final String KS_FETCH_ISSUER_FAILED = "Issuer fetching failed.";
    public static final String KS_FETCH_CERT_FAILED = "Certificate fetching failed.";
    public static final String ALIAS_DOES_NOT_EXIST = "Given alias does not exist in key store.";
    public static final String ERROR_WITH_CERTIFICATE_READING = "Oops, error happened with reading the certificate. Try again later.";
}
