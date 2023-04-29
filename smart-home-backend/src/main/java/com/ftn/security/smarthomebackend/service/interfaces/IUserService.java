package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import com.ftn.security.smarthomebackend.enums.Role;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.model.BlacklistedJWT;
import com.ftn.security.smarthomebackend.model.User;

import java.io.IOException;

public interface IUserService {
    User getVerifiedUser(String email) throws EntityNotFoundException;
    UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            Role role
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException;
    boolean checkIfUserAlreadyExists(String email);
    boolean activate(String verifyId, int securityCode) throws EntityNotFoundException, WrongVerifyTryException;
    User save(User user);
    void updateUsersJWTBlacklist(User user, BlacklistedJWT jwt);
    void removeExpiredJWTsFromUserBlacklist(User user);
}
