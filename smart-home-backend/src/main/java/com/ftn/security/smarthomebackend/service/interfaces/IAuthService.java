package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.exception.InvalidCredsException;
import com.ftn.security.smarthomebackend.exception.InvalidJWTException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;


public interface IAuthService {
    LoginResponse login(final String email, final String password, final HttpServletResponse response) throws InvalidCredsException;
    void logout(HttpServletRequest request);
}
