package com.ftn.security.smarthomebackend.repositories;

import com.ftn.security.smarthomebackend.models.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
}
