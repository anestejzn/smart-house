package com.ftn.security.smarthomebackend.services.implementation;

import com.ftn.security.smarthomebackend.enums.AccountType;
import com.ftn.security.smarthomebackend.exception.EntityAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.PasswordsDoNotMatchException;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.repository.UserRepository;
import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.ftn.security.smarthomebackend.util.Helper.passwordsDontMatch;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegularUserService regularUserService;

    public boolean checkIfUserAlreadyExists(String email) {
        Optional<User> user = userRepository.getUserByEmail(email);

        return user.isPresent();
    }

    public UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            AccountType role,
            String country,
            String city
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException {
        if (passwordsDontMatch(password, confirmPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (this.checkIfUserAlreadyExists(email)) {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists.", email));
        }

        return regularUserService.create(
           email, name, surname, password, role, country, city
        );
    }

}
