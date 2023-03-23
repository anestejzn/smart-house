package com.ftn.security.smarthomebackend.models;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.AccountType;
import jakarta.persistence.Column;
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
    @Column(name="verified", nullable = false)
    protected Boolean verified;
    @Column(name="account_type", nullable = false)
    protected AccountType accountType;

    public RegularUser(String email, String password, String name, String surname, String salt, AccountStatus status, int failedAttempts, LocalDateTime lockedUntil, Boolean verified, AccountType accountType) {
        super(email, password, name, surname, salt, status, failedAttempts, lockedUntil);
        this.verified = verified;
        this.accountType = accountType;
    }
}
