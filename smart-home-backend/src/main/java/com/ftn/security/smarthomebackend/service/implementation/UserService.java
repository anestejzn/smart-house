package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.EntityType;

import com.ftn.security.smarthomebackend.model.*;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.repository.UserRepository;
import com.ftn.security.smarthomebackend.security.JWTUtils;
import com.ftn.security.smarthomebackend.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.ftn.security.smarthomebackend.dto.response.UserDTO.fromUserListToDTO;
import static com.ftn.security.smarthomebackend.util.Constants.PASSWORDS_FILE_PATH;
import static com.ftn.security.smarthomebackend.util.Helper.passwordsDontMatch;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegularUserService regularUserService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private RoleService roleService;

    @Override
    public User getVerifiedUser(String email) throws EntityNotFoundException {
        return userRepository.getVerifiedUser(email)
                .orElseThrow(() -> new EntityNotFoundException(email, EntityType.USER));
    }


    @Override
    public boolean checkIfUserAlreadyExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String roleName
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException, MostCommonPasswordException {
        if (passwordsDontMatch(password, confirmPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (this.checkIfUserAlreadyExists(email)) {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists.", email));
        }

        if(isMostCommonPassword(password)){
            throw new MostCommonPasswordException();
        }

        verificationService.create(email);
        Role role = roleService.getRoleByName(roleName);

        return regularUserService.create(
                email, name, surname, password, role
        );
    }

    @Override
    public boolean activate(String verifyId, int securityCode) throws EntityNotFoundException, WrongVerifyTryException {
        RegistrationVerification verify = verificationService.update(verifyId, securityCode);
        regularUserService.activateAccount(verify.getUserEmail());
        return true;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUsersJWTBlacklist(User user, BlacklistedJWT jwt) {
        if (user.getBlacklistedJWTs() == null)
            user.setBlacklistedJWTs(new ArrayList<>());

        user.getBlacklistedJWTs().add(jwt);
        save(user);
    }

    @Override
    public void removeExpiredJWTsFromUserBlacklist(User user) {
        user.setBlacklistedJWTs(
            user.getBlacklistedJWTs()
                .stream()
                .filter(blacklistedJWT -> !JWTUtils.jwtHasExpired(blacklistedJWT.getJwt()))
                .collect(Collectors.toList())
        );
        save(user);
    }

    @Override
    public List<UserDTO> getAllActiveOwnerUsers() {

        return fromUserListToDTO(userRepository.getAllActiveOwnerUsers());
    }

    @Override
    public List<UserDTO> getAllCertifiedOwnerUsers() {

        return fromUserListToDTO(userRepository.getAllCertifiedOwnerUsers());
    }

    @Override
    public List<UserDTO> getAllActiveTenantUsers() {

        return fromUserListToDTO(userRepository.getAllActiveTenantUsers());
    }

    @Override
    public List<UserDTO> filterUsers(boolean ascending, AccountStatus status) {

        return fromUserListToDTO(ascending ? userRepository.filterAscendingUsers(status)
                : userRepository.filterDescendingUsers(status));
    }

    private boolean isMostCommonPassword(String password) throws FileNotFoundException {
        File file = new File(PASSWORDS_FILE_PATH);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String mostCommonPassword = reader.nextLine();
            if(mostCommonPassword.equals(password)){
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }


}
