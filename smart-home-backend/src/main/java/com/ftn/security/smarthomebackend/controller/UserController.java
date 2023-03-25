package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.exception.EntityAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.PasswordsDoNotMatchException;
import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import com.ftn.security.smarthomebackend.dto.request.RegularUserRegistrationRequest;
import com.ftn.security.smarthomebackend.service.implementation.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody RegularUserRegistrationRequest request)
            throws PasswordsDoNotMatchException, EntityAlreadyExistsException
    {

        return userService.create(
            request.getEmail(),
            request.getName(),
            request.getSurname(),
            request.getPassword(),
            request.getConfirmPassword(),
            request.getRole(),
            request.getCountry(),
            request.getCity()
        );
    }


}
