package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.AlarmResponse;
import com.ftn.security.smarthomebackend.dto.response.ReportResponse;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.Alarm;
import java.time.LocalDateTime;
import java.util.List;

public interface IAlarmService {

    void saveAll(List<Alarm> alarms);

    List<AlarmResponse> getAllAlarms();

    List<AlarmResponse> getFilteredAlarms(Long realEstateId, Long deviceId, int time) throws EntityNotFoundException, CannotPerformActionException;

    List<ReportResponse> getReportData(Long userId, LocalDateTime startTime, LocalDateTime endTime) throws EntityNotFoundException, CannotPerformActionException;
}
