package com.ftn.security.smarthomebackend.enums;

public enum EntityType {
    USER,
    VERIFY;

    public static String getEntityErrorMessage(String id, EntityType entityType) {
        if (entityType == EntityType.USER) {
            return "User: " + id + " is not found.";
        } else if (entityType == EntityType.VERIFY) {
            return "Verify is not found";
        }
        else{
            return "Entity is not found";
        }
    }
}
