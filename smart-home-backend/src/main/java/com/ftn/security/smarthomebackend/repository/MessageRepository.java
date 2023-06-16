package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select distinct m from Message m where m.deviceId=?1 and m.dateTime <=?2")
    List<Message> getMessagesForDevice(Long deviceId, LocalDateTime readUntil);
}
