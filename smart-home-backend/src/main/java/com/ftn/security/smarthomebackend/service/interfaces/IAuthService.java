package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.MailCannotBeSentException;
import com.ftn.security.smarthomebackend.exception.UserLockedException;
import com.ftn.security.smarthomebackend.exception.WrongVerifyTryException;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface IAuthService {

    LoginResponse login(String email, String password) throws UserLockedException;
    void generatePin(String pin) throws EntityNotFoundException, IOException, MailCannotBeSentException;
    boolean confirmPin(String email, String pin) throws EntityNotFoundException, WrongVerifyTryException;

    boolean incrementFailedAttempts(String email) throws EntityNotFoundException;
}
