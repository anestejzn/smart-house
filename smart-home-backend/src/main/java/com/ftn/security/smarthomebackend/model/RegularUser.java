package com.ftn.security.smarthomebackend.model;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="regular_user")
@Setter
@Getter
@NoArgsConstructor
public class RegularUser extends User {


    public RegularUser(String email, String password, String name, String surname, String salt, AccountStatus status, int failedAttempts, LocalDateTime lockedUntil, Role role) {
        super(email, password, name, surname, salt, status, failedAttempts, lockedUntil, role);
    }
}
