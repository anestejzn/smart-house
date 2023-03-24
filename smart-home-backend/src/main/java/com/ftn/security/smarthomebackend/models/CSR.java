package com.ftn.security.smarthomebackend.models;

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
    @Column(name="email", nullable = false)
    protected String email;
    @Column(name="status", nullable = false)
    protected CSRStatus status;

}
