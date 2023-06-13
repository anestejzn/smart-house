package com.ftn.security.smarthomebackend.util;

public class LogGenerator {

    public static String successfulLogin(String email) {
        return String.format("User with email: '%s' successfully logged in.", email);
    }

    public static String badCredentialLogin(String email) {
        return String.format("Login failed with bad credentials for email: '%s'.", email);
    }

    public static String unsuccessfulLogin(String email) {
        return String.format("Login failed for email: '%s'.", email);
    }

    public static String accountLockedLogin(String email) {
        return String.format("Login failed for locked account for email: '%s'.", email);
    }

    public static String unuccessfulLogin(String email) {
        return String.format("User with email: '%s' have unsuccessful verify.", email);
    }


    public static String notFoundCertificate(String alias){
        return String.format("Certificate with alias: '%s' is not found", alias);
    }

    public static String existAlias(String alias){
        return String.format("Certificate with alias: '%s' is already existed.", alias);
    }

    public static String createdLeafCertificae(String email){
        return String.format("User with email: '%s' get certificate.", email);
    }

    public static String createdRootCertificae(){
        return "Root certificate is created.";
    }

    public static String createdIntermediateCertificae(){
        return "Intermediate certificate is created.";
    }

    public static String createdCsr(String email){
        return String.format("Csr is created for user with email: '%s'", email);
    }

    public static String rejectedCsr(String email){
        return String.format("Csr is rejected for user with email: '%s'", email);
    }

    public static String createdRealEstate(){
        return "New real estate is added.";
    }

    public static String editedRealEstate(Long id){
        return String.format("Real estate with '%d' is edited.", id);
    }

    public static String editedOwnerRealEstate(Long id){
        return String.format("Owner for real estate with '%d' is edited.", id);
    }

    public static String deletedRealEstate(Long id){
        return String.format("Real estate with '%d' is deleted.", id);
    }

    public static String editedTenantsRealEstate(Long id){
        return String.format("Tenants for real estate with '%d' is edited.", id);
    }

    public static String createdNewUser(String email){
        return String.format("Added user with email '%s'.", email);
    }

    public static String activatedAccount(String email){
        return String.format("Account with email '%s' is activated.", email);
    }

    public static String passwordsNotMatch(){
        return "Passwords don't match.";
    }

    public static String alreadyExistUser(String email){
        return String.format("User with email '%s' already exist. Unsuccessfully registration..", email);
    }

    public static String mostCommonPassword(String email){
        return String.format("User with email '%s' entered the most common password. Unsuccessfully registration..", email);
    }

    public static String notFoundVerify(Long id){
        return String.format("Verification with '%d' is not found", id);
    }

    public static String notFoundHashedVerify(String id){
        return String.format("Verification with hash '%s' is not found", id);
    }

    public static String successfulVerify(String email){
        return String.format("User with email '%s' is verified", email);
    }

    public static String unsuccessfulVerify(String email){
        return String.format("User with email '%s' have wrong verify try.", email);
    }

    public static String notHaveMoreTries(String email){
        return String.format("User with email '%s' don't have more verify tries.", email);
    }

    public static String createdVerify(String email){
        return String.format("Created verify code for user with email '%s'", email);
    }

    public static String createdNewVerify(String email){
        return String.format("Created new verify code for user with email '%s'", email);
    }

    public static String maliciousAddress(String email) {
        return String.format("User with email: '%s' try to login from malicious address.", email);
    }

    public static String notFoundDevice(Long id){
        return String.format("Device with id: '%d' is not found", id);
    }

}
