package com.ftn.security.smarthomebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="blacklisted_jwt")
@Getter
@Setter
@NoArgsConstructor
public class BlacklistedJWT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="jwt", nullable = false, length = 700)
    protected String jwt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected User user;

    public BlacklistedJWT(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }
}
