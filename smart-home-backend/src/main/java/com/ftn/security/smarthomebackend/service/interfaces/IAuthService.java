package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.exception.InvalidCredsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.MailCannotBeSentException;
import com.ftn.security.smarthomebackend.exception.UserLockedException;
import com.ftn.security.smarthomebackend.exception.WrongVerifyTryException;
import java.io.IOException;


public interface IAuthService {
    void logout(HttpServletRequest request);
    LoginResponse loginAdmin(final String email, final String password, final HttpServletRequest request, final HttpServletResponse response) throws UserLockedException, InvalidCredsException, EntityNotFoundException;
    LoginResponse loginRegularUser(final String email, final String password, final HttpServletRequest request, final HttpServletResponse response) throws UserLockedException, InvalidCredsException, EntityNotFoundException;
    void generatePin(String pin) throws EntityNotFoundException, IOException, MailCannotBeSentException;
    void confirmPin(String email, String pin) throws EntityNotFoundException, WrongVerifyTryException, UserLockedException;
    void incrementFailedAttempts(String email) throws EntityNotFoundException, UserLockedException;
}
