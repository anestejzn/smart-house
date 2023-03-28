package com.ftn.security.smarthomebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = PasswordsDoNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String passwordsDoNotMatchException(PasswordsDoNotMatchException passwordsDoNotMatchException) {

        return passwordsDoNotMatchException.getMessage();
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String entityAlreadyExistsException(EntityAlreadyExistsException entityAlreadyExists) {

        return entityAlreadyExists.getMessage();
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noSuchElementException() {

        return "Required element cannot be found.";
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        Optional<FieldError> error = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().findFirst();

        return error.map(
            fieldError -> String.format("%s", fieldError.getDefaultMessage()))
            .orElse("Error not found");
    }

    @ExceptionHandler(value = WrongVerifyTryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String wrongVerifyTryException(WrongVerifyTryException wrongVerifyTryException) {

        return wrongVerifyTryException.getMessage();
    }

    @ExceptionHandler(value = MailCannotBeSentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String mailCannotBeSentException(MailCannotBeSentException mailCannotBeSentException) {

        return mailCannotBeSentException.getMessage();
    }

    @ExceptionHandler(value = KeyStoreException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String keyStoreException(KeyStoreException keyStoreException) {

        return "Error with key store happened.";
    }

    @ExceptionHandler(value = CertificateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String certificateException(CertificateException certificateException) {

        return "Error with certificate happened.";
    }

}
