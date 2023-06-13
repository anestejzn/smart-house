package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.UserDTO;
import com.ftn.security.smarthomebackend.model.Role;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.RegularUser;

public interface IRegularUserService {

    RegularUser getRegularUserByEmail(String email) throws EntityNotFoundException;
    UserDTO create(
            String email,
            String name,
            String surname,
            String password,
            Role role);
    boolean activateAccount(String userEmail) throws EntityNotFoundException;
    RegularUser getRegularUserById(Long id) throws EntityNotFoundException;
    boolean block(RegularUser regularUser);

    boolean unblock(Long userId) throws EntityNotFoundException;
}
