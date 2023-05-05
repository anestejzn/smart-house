package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email=?1 and u.verified=true")
    Optional<User> getVerifiedUser(String email);

    @Query("select u from User u where u.role = 0 and u.status = 0 and u.verified=true")
    List<User> getAllActiveRegularUsers();

    Optional<User> findByEmail(String email);

}
