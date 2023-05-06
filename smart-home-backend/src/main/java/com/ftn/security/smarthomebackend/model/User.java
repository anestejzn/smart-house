package com.ftn.security.smarthomebackend.model;

import com.ftn.security.smarthomebackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class User {
    @Id
    @SequenceGenerator(name = "generator1", sequenceName = "usersIdGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator1")
    protected Long id;

    @Column(name="email", nullable = false, unique = true)
    protected String email;
    @Column(name="password", nullable = false)
    protected String password;

    @Column(name="name", nullable = false)
    protected String name;

    @Column(name="surname", nullable = false)
    protected String surname;

    @Column(name="salt", nullable = false)
    protected String salt;

    @Column(name="status", nullable = false)
    protected AccountStatus status;

    @Column(name="failed_attempts", nullable = false)
    protected Integer failedAttempts;

    @Column(name="locked_until")
    protected LocalDateTime lockedUntil;
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    protected Role role;
    @Column(name="verified", nullable = false)
    protected Boolean verified=false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    protected List<BlacklistedJWT> blacklistedJWTs;

    @Column(name="pin")
    protected String pin;

    public User(
            String email,
            String password,
            String name,
            String surname,
            String salt,
            AccountStatus status,
            int failedAttempts,
            LocalDateTime lockedUntil,
            Role role
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.salt = salt;
        this.status = status;
        this.failedAttempts = failedAttempts;
        this.lockedUntil = lockedUntil;
        this.role = role;
    }
}