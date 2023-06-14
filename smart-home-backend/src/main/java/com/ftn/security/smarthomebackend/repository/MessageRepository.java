package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
