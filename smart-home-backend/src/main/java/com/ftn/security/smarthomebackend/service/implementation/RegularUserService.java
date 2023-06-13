package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.model.Role;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.repository.RegularUserRepository;
import com.ftn.security.smarthomebackend.dto.response.UserDTO;

import com.ftn.security.smarthomebackend.service.interfaces.ILogService;

import com.ftn.security.smarthomebackend.service.interfaces.IRegularUserService;
import com.ftn.security.smarthomebackend.util.LogGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import static com.ftn.security.smarthomebackend.util.Constants.ZERO_FAILED_ATTEMPTS;

import static com.ftn.security.smarthomebackend.util.Constants.SALT_LENGTH;
import static com.ftn.security.smarthomebackend.util.Helper.generateRandomString;
import static com.ftn.security.smarthomebackend.util.Helper.getHash;

@Component
public class RegularUserService implements IRegularUserService {

    @Autowired
    private RegularUserRepository regularUserRepository;
    @Autowired
    private ILogService logService;

    public RegularUser getRegularUserByEmail(String email) throws EntityNotFoundException {
        return regularUserRepository.getRegularUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email, EntityType.USER));
    }

    public RegularUser getRegularUserById(Long id) throws EntityNotFoundException {
        return regularUserRepository.getRegularUserById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.USER));
    }

    public UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            Role role
    ) {
        String salt = generateRandomString(SALT_LENGTH);
        String hashedPassword = getHash(password);
        RegularUser regularUser = regularUserRepository.save(
                new RegularUser(email, hashedPassword, name, surname, salt, AccountStatus.NON_CSR,
                        ZERO_FAILED_ATTEMPTS, null, role
        ));

        logService.generateLog(LogGenerator.createdNewUser(email), LogLevel.INFO);
        return new UserDTO(regularUser);
    }

    public boolean activateAccount(String userEmail) throws EntityNotFoundException {
        RegularUser regularUser = this.getRegularUserByEmail(userEmail);
        regularUser.setVerified(true);
        regularUserRepository.save(regularUser);
        logService.generateLog(LogGenerator.activatedAccount(userEmail), LogLevel.INFO);
        return true;
    }

    public boolean block(RegularUser regularUser) {
        regularUser.setStatus(AccountStatus.BLOCKED);
        regularUserRepository.save(regularUser);

        return true;
    }

    public boolean unblock(Long userId) throws EntityNotFoundException {
        RegularUser regularUser = this.getRegularUserById(userId);
        regularUser.setStatus(AccountStatus.ACTIVE);

        regularUserRepository.save(regularUser);

        return true;
    }

}
