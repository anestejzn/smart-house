package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.AlarmResponse;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;

import java.util.List;

public interface IAlarmService {


    List<AlarmResponse> getFilteredAlarms(Long realEstateId, Long deviceId, int time) throws EntityNotFoundException, CannotPerformActionException;
}
