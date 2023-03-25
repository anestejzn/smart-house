package com.ftn.security.smarthomebackend.exceptions;

public class AppException extends Exception {

    private final String message;

    public AppException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
