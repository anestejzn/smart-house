package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.ConfirmPinRequest;
import com.ftn.security.smarthomebackend.dto.request.LoginRequest;
import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.MailCannotBeSentException;
import com.ftn.security.smarthomebackend.exception.UserLockedException;
import com.ftn.security.smarthomebackend.exception.WrongVerifyTryException;
import com.ftn.security.smarthomebackend.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.*;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping(path="/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody final LoginRequest loginRequest) throws UserLockedException {

        return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping("/generate-pin/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void generatePin(@PathVariable final String email) throws EntityNotFoundException, IOException, MailCannotBeSentException {
        authService.generatePin(email);
    }

    @PutMapping("/confirm-pin")
    @ResponseStatus(HttpStatus.OK)
    public boolean confirmPin(@Valid @RequestBody final ConfirmPinRequest confirmPinRequest) throws WrongVerifyTryException, EntityNotFoundException {
        return authService.confirmPin(confirmPinRequest.getEmail(), confirmPinRequest.getPin());
    }

    @GetMapping("/increment-failed-attempts/{email}")
    @ResponseStatus(HttpStatus.OK)
    public boolean incrementFailedAttempts(@PathVariable final String email) throws EntityNotFoundException {
        return authService.incrementFailedAttempts(email);
    }

}
