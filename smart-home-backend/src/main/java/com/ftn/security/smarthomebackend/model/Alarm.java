package com.ftn.security.smarthomebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="message")
    private String message;

    @Column(name="device_id")
    private Long deviceId;

    @Column(name="date_time")
    private LocalDateTime dateTime;

    @Column(name="admin_only")
    private boolean adminOnly;

    public Alarm(String message, Long deviceId, LocalDateTime dateTime, boolean adminOnly) {
        this.message = message;
        this.deviceId = deviceId;
        this.dateTime = dateTime;
        this.adminOnly = adminOnly;
    }
}
