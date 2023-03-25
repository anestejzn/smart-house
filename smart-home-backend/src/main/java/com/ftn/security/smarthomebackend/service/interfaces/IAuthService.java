package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import org.springframework.stereotype.Service;


public interface IAuthService {

    LoginResponse login(String email, String password);
}
