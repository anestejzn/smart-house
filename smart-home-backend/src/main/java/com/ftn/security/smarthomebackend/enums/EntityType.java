package com.ftn.security.smarthomebackend.enums;

public enum EntityType {
    USER,
    VERIFY,
    CERTIFICATE,
    CSR,
    DEVICE,
    REAL_ESTATE;
    public static String getEntityErrorMessage(String id, EntityType entityType) {
        if (entityType == EntityType.USER) {
            return "User is not found.";
        } else if (entityType == EntityType.VERIFY) {
            return "Verify is not found";
        }
        else if(entityType == EntityType.CERTIFICATE){
            return "Certificate with alias is not found.";
        }
        else if (entityType == EntityType.CSR) {
            return "Csr is not found.";
        }
        else if (entityType == EntityType.DEVICE) {
            return "Device is not found.";
        }
        else if (entityType == EntityType.REAL_ESTATE) {
            return "Rela estate is not found.";
        }
        else{
            return "Entity is not found";
        }
    }
}
