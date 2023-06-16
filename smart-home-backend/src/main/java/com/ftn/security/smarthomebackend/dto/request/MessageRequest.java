package com.ftn.security.smarthomebackend.dto.request;

import com.ftn.security.smarthomebackend.enums.DeviceType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequest {

    @NotBlank(message="Message can not be empty.")
    private String messageText;

    @Future(message="Date time can not be in future.")
    private LocalDateTime dateTime;

    @NotNull(message="Message have to have device id.")
    private Long deviceId;

    @NotNull(message="Message have to have device type.")
    private DeviceType deviceType;

    public MessageRequest(String messageText, LocalDateTime dateTime, Long deviceId, DeviceType deviceType) {
        this.messageText = messageText;
        this.dateTime = dateTime;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
    }
}
