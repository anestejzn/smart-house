package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {

    Optional<RegularUser> getRegularUserByEmail(String email);


}
