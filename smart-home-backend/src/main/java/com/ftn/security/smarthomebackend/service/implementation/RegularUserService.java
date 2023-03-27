package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.enums.Role;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.repository.RegularUserRepository;
import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.ftn.security.smarthomebackend.util.Constants.ZERO_FAILED_ATTEMPTS;

import static com.ftn.security.smarthomebackend.util.Constants.SALT_LENGTH;
import static com.ftn.security.smarthomebackend.util.Helper.generateRandomString;
import static com.ftn.security.smarthomebackend.util.Helper.getHash;

@Component
public class RegularUserService {

    @Autowired
    private RegularUserRepository regularUserRepository;

    public RegularUser getRegularUserByEmail(String email) throws EntityNotFoundException {
        return regularUserRepository.getRegularUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email, EntityType.USER));
    }

    public UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            Role role
    ) {
        String salt = generateRandomString(SALT_LENGTH);
        String hashedPassword = getHash(salt + password);
        RegularUser regularUser = regularUserRepository.save(
                new RegularUser(email, hashedPassword, name, surname, salt, AccountStatus.NON_CERTIFICATED,
                        ZERO_FAILED_ATTEMPTS, null, role
        ));

        return new UserDTO(regularUser);
    }

    public boolean activateAccount(String userEmail) throws EntityNotFoundException {
        RegularUser regularUser = this.getRegularUserByEmail(userEmail);
        regularUser.setVerified(true);
        regularUserRepository.save(regularUser);

        return true;
    }
}
