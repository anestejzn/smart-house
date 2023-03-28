package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.CancelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CancelCertificateRepository extends JpaRepository<CancelCertificate, Long> {


    Optional<CancelCertificate> findByAlias(String alias);
}
