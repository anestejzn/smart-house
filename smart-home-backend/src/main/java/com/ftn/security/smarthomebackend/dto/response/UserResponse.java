package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.Role;
import com.ftn.security.smarthomebackend.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private AccountStatus accountStatus;

    public UserResponse(Long id, String email, String password, String name, String surname, AccountStatus accountStatus, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.accountStatus = accountStatus;
        this.role = role;
    }

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRole();
        this.accountStatus = user.getStatus();
    }
}
