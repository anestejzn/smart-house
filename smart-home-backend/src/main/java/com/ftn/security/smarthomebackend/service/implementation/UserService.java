package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.enums.Role;
import com.ftn.security.smarthomebackend.exception.EntityAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.PasswordsDoNotMatchException;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.repository.UserRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ftn.security.smarthomebackend.util.Helper.passwordsDontMatch;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegularUserService regularUserService;

    public User getVerifiedUser(String email) throws EntityNotFoundException {
        return userRepository.getVerifiedUser(email)
                .orElseThrow(() -> new EntityNotFoundException(email, EntityType.USER));

    }

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
            Role role,
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
