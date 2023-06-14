package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.CancelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CancelCertificateRepository extends JpaRepository<CancelCertificate, Long> {

    Optional<CancelCertificate> findByAlias(String alias);

    @Query("select cc from CancelCertificate cc where cc.alias=?1 and cc.mostRecent=true")
    Optional<CancelCertificate> findMostRecentByAlias(String alias);
}
