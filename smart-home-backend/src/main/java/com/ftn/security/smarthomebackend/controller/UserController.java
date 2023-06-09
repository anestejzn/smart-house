package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.VerifyRequest;
import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import com.ftn.security.smarthomebackend.dto.request.RegularUserRegistrationRequest;
import com.ftn.security.smarthomebackend.service.interfaces.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("all-active-owners")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('GET_ACTIVE_OWNERS')")
    public List<UserDTO> getAllActiveOwnerUsers() {

        return userService.getAllActiveOwnerUsers();
    }

    @GetMapping("all-active-tenants")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('GET_ACTIVE_TENANTS')")
    public List<UserDTO> getAllActiveTenantUsers() {

        return userService.getAllActiveTenantUsers();
    }

    @GetMapping("all-certified-owners")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('GET_ALL_OWNERS')")
    public List<UserDTO> getAllCertifiedOwnerUsers() {

        return userService.getAllCertifiedOwnerUsers();
    }

    @GetMapping(value = "/{ascending}/{status}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('FILTER_USERS')")
    public List<UserDTO> filterUsers(@PathVariable @Valid boolean ascending,
                                   @PathVariable @Valid @NotBlank AccountStatus status)
    {

        return userService.filterUsers(
                ascending,
                status
        );
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody RegularUserRegistrationRequest request) throws PasswordsDoNotMatchException, EntityAlreadyExistsException, IOException, MailCannotBeSentException, MostCommonPasswordException {
        return userService.create(
            request.getEmail(),
            request.getName(),
            request.getSurname(),
            request.getPassword(),
            request.getConfirmPassword(),
            request.getRole()
        );
    }

    @PutMapping("/activate-account")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@Valid @RequestBody VerifyRequest verifyRequest) throws EntityNotFoundException, WrongVerifyTryException {
        return userService.activate(verifyRequest.getVerifyId(), verifyRequest.getSecurityCode());
    }

}
