package com.ftn.security.smarthomebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="message_text")
    private String messageText;

    @Column(name="date_time")
    private LocalDateTime dateTime;

    @Column(name="device_id")
    private Long deviceId;

    public Message(String messageText, LocalDateTime dateTime, Long deviceId) {
        this.messageText = messageText;
        this.dateTime = dateTime;
        this.deviceId = deviceId;
    }
}
