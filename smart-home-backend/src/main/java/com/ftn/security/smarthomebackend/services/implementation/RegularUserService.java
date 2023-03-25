package com.ftn.security.smarthomebackend.services.implementation;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.AccountType;
import com.ftn.security.smarthomebackend.models.RegularUser;
import com.ftn.security.smarthomebackend.repositories.RegularUserRepository;
import com.ftn.security.smarthomebackend.transfer_objects.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.ftn.security.smarthomebackend.util.Constants.ZERO_FAILED_ATTEMPTS;

import java.time.LocalDateTime;

import static com.ftn.security.smarthomebackend.util.Constants.SALT_LENGTH;
import static com.ftn.security.smarthomebackend.util.Helper.generateRandomString;
import static com.ftn.security.smarthomebackend.util.Helper.getHashedNewUserPassword;

@Component
public class RegularUserService {

    @Autowired
    private RegularUserRepository regularUserRepository;

    public UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            AccountType role,
            String country,
            String city
    ) {
        String salt = generateRandomString(SALT_LENGTH);
        String hashedPassword = getHashedNewUserPassword(salt + password);
        RegularUser regularUser = regularUserRepository.save(
                new RegularUser(email, hashedPassword, name, surname, salt, AccountStatus.NON_CERTIFICATED,
                        ZERO_FAILED_ATTEMPTS, null, country, city, false, role
        ));

        return new UserDTO(regularUser);
    }
}
