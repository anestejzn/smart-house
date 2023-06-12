package com.ftn.security.smarthomebackend.dto.response;


import com.ftn.security.smarthomebackend.model.Alarm;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponse {

    private Long id;
    private String message;
    private Long deviceId;
    private LocalDateTime dateTime;
    private String deviceName;

    public AlarmResponse(Alarm alarm, String deviceName) {
        this.id = alarm.getId();
        this.message = alarm.getMessage();
        this.deviceId = alarm.getDeviceId();
        this.dateTime = alarm.getDateTime();
        this.deviceName = deviceName;
    }

    public static List<AlarmResponse> fromAlarmsToResponses(List<Alarm> alarms, String deviceName) {
        List<AlarmResponse> responses = new LinkedList<>();
        alarms.forEach(alarm -> {
            responses.add(new AlarmResponse(alarm, deviceName));
        });

        return responses;
    }

}
