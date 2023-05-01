package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
public class UserDTO {

    private Long id;

    private final String email;

    private final String name;

    private final String surname;

    public UserDTO(
            String email,
            String name,
            String surname
    ) {
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
    }

    public UserDTO(RegularUser regularUser) {
        this.id = regularUser.getId();
        this.email = regularUser.getEmail();
        this.name = regularUser.getName();
        this.surname = regularUser.getSurname();
    }

    public static List<UserDTO> fromUserListToDTO(List<User> users) {
        List<UserDTO> userDTOs = new LinkedList<>();
        users.forEach(user ->
                userDTOs.add(new UserDTO(user))
        );

        return userDTOs;
    }
}