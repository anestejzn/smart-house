package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.RegistrationVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<RegistrationVerification, Long> {

    Optional<RegistrationVerification> getRegistrationVerificationsById(Long id);
    Optional<RegistrationVerification> getRegistrationVerificationsByHashedId(String id);

}
