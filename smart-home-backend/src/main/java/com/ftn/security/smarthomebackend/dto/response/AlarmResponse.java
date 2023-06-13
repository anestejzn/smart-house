package com.ftn.security.smarthomebackend.dto.response;

import com.ftn.security.smarthomebackend.model.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AlarmResponse {
    private LocalDateTime dateTime;
    private Device device;
    private String message;

    public AlarmResponse(LocalDateTime dateTime, Device device, String message) {
        this.dateTime = dateTime;
        this.device = device;
        this.message = message;
    }
}
