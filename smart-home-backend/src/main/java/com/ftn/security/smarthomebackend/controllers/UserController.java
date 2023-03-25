package com.ftn.security.smarthomebackend.controllers;

import com.ftn.security.smarthomebackend.exceptions.EntityAlreadyExistsException;
import com.ftn.security.smarthomebackend.exceptions.PasswordsDoNotMatchException;
import com.ftn.security.smarthomebackend.services.implementation.UserService;
import com.ftn.security.smarthomebackend.transfer_objects.dtos.UserDTO;
import com.ftn.security.smarthomebackend.transfer_objects.requests.RegularUserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:4201")
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
