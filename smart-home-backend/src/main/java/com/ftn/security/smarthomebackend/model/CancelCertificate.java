package com.ftn.security.smarthomebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cancel_certificate")
@Getter
@Setter
@NoArgsConstructor
public class CancelCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private String reason;

    @Column(name = "most_recent", nullable = false)
    private boolean mostRecent = true;

    public CancelCertificate(String alias, String reason) {
        this.alias = alias;
        this.reason = reason;
    }
}
