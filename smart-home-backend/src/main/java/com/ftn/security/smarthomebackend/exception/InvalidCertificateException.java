package com.ftn.security.smarthomebackend.exception;

import static com.ftn.security.smarthomebackend.util.Constants.INVALID_CERTIFICATE_EXCEPTION;
import static com.ftn.security.smarthomebackend.util.Constants.PASSWORDS_DO_NOT_MATCH_MESSAGE;

public class InvalidCertificateException extends AppException {

    public InvalidCertificateException() {
        super(INVALID_CERTIFICATE_EXCEPTION);
    }

    public InvalidCertificateException(String message) {
        super(message);
    }
}
