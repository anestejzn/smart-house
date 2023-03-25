package com.ftn.security.smarthomebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name="registration_verification")
@Getter
@Setter
@NoArgsConstructor
public class RegistrationVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name="security_code", nullable = false)
    protected String securityCode;
    @Column(name="failed_attempts", nullable = false)
    protected Integer failedAttempts;
    @Column(name="user_email", nullable = false, unique = true)
    protected String userEmail;
    @Column(name="expires", nullable = false)
    protected LocalDateTime expires;

    public RegistrationVerification(String securityCode, Integer failedAttempts, String userEmail, LocalDateTime expires) {
        this.securityCode = securityCode;
        this.failedAttempts = failedAttempts;
        this.userEmail = userEmail;
        this.expires = expires;
    }
}
