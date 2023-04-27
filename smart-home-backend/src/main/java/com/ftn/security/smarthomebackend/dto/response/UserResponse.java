package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.Role;
import com.ftn.security.smarthomebackend.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime lockedUntil;

    public UserResponse(Long id, String email, String password, String name, String surname, AccountStatus accountStatus, Role role, LocalDateTime lockedUntil) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.accountStatus = accountStatus;
        this.role = role;
        this.lockedUntil = lockedUntil;
    }

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRole();
        this.accountStatus = user.getStatus();
        this.lockedUntil = user.getLockedUntil();
    }
}
