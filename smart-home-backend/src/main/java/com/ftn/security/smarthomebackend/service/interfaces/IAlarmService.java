package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.AlarmResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.Alarm;

import java.util.List;

public interface IAlarmService {

    void saveAll(List<Alarm> alarms);

    List<AlarmResponse> getAllAlarms() throws EntityNotFoundException;
}
