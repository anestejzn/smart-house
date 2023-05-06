package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import com.ftn.security.smarthomebackend.model.Role;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.model.BlacklistedJWT;
import com.ftn.security.smarthomebackend.model.User;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User getVerifiedUser(String email) throws EntityNotFoundException;
    UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String role
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException, MostCommonPasswordException;
    boolean checkIfUserAlreadyExists(String email);
    boolean activate(String verifyId, int securityCode) throws EntityNotFoundException, WrongVerifyTryException;
    User save(User user);
    void updateUsersJWTBlacklist(User user, BlacklistedJWT jwt);
    void removeExpiredJWTsFromUserBlacklist(User user);

    List<UserDTO> getAllActiveOwnerUsers();

    List<UserDTO> getAllCertifiedOwnerUsers();

    List<UserDTO> getAllActiveTenantUsers();
}
