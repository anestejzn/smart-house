package com.ftn.security.smarthomebackend.exception;

import com.ftn.security.smarthomebackend.enums.EntityType;

import static com.ftn.security.smarthomebackend.enums.EntityType.getEntityErrorMessage;

public class AppException extends Exception{

    private final String message;

    public AppException(String id, EntityType entityType) {
        super();
        this.message = createExceptionMessage(id, entityType);
    }

    public AppException(String message) {
        super();
        this.message = message;
    }

    private String createExceptionMessage(String id, EntityType entityType) {
        return getEntityErrorMessage(id, entityType);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
