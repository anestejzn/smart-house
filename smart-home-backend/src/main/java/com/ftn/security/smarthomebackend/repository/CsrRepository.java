package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.dto.response.CsrResponse;
import com.ftn.security.smarthomebackend.model.CSR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsrRepository extends JpaRepository<CSR, Long> {

    @Query("select c from CSR c where c.status = 0")
    List<CSR> findByPendingStatus();
}
