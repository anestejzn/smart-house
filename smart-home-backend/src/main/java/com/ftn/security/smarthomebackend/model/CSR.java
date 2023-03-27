package com.ftn.security.smarthomebackend.model;

import com.ftn.security.smarthomebackend.enums.CSRStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="csr")
@Getter
@Setter
@NoArgsConstructor
public class CSR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private String commonName;

    @Column(nullable = false)
    private String organizationUnit;

    @Column(nullable = false)
    private String organization;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(name="status", nullable = false)
    protected CSRStatus status;

    public CSR(User user, String commonName, String organizationUnit, String organization, String city, String state, String country, CSRStatus status) {
        this.user = user;
        this.commonName = commonName;
        this.organizationUnit = organizationUnit;
        this.organization = organization;
        this.city = city;
        this.state = state;
        this.country = country;
        this.status = status;
    }
}
