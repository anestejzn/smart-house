package com.ftn.security.smarthomebackend.enums;

public enum EntityType {
    USER,
    VERIFY,
    CERTIFICATE,
    CSR;
    public static String getEntityErrorMessage(String id, EntityType entityType) {
        if (entityType == EntityType.USER) {
            return "User: " + id + " is not found.";
        } else if (entityType == EntityType.VERIFY) {
            return "Verify is not found";
        }
        else if(entityType == EntityType.CERTIFICATE){
            return "Certificate with alias: " + id + " is not found.";
        }
        else if (entityType == EntityType.CSR) {
            return "Csr: " + id + " is not found.";
        }
        else{
            return "Entity is not found";
        }
    }
}
