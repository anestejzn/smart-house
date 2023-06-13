package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.AlarmResponse;
import com.ftn.security.smarthomebackend.dto.response.ReportResponse;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.Device;
import com.ftn.security.smarthomebackend.model.RealEstate;
import com.ftn.security.smarthomebackend.repository.AlarmRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IAlarmService;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.ftn.security.smarthomebackend.dto.response.AlarmResponse.fromAlarmsToResponses;
import static com.ftn.security.smarthomebackend.util.Constants.*;

@Service
public class AlarmService implements IAlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private IRealEstateService realEstateService;

    @Override
    public List<AlarmResponse> getFilteredAlarms(Long realEstateId, Long deviceId, int time)
            throws EntityNotFoundException, CannotPerformActionException
    {
        RealEstate realEstate = realEstateService.getRealEstateById(realEstateId);
        LocalDateTime bottomDate = getBottomTime(time);

        return deviceId == SHOW_ALL ? showAlarmsForAllDevices(realEstate, bottomDate)
                : showAlarmsForCertainDevice(realEstate, deviceId, bottomDate);
    }

    @Override
    public List<ReportResponse> getReportData(Long userId, LocalDateTime startTime, LocalDateTime endTime)
            throws EntityNotFoundException, CannotPerformActionException {
        checkDateData(startTime, endTime);
        List<RealEstate> realEstates = realEstateService.getRealEstatesForOwner(userId);
        List<ReportResponse> responses = new LinkedList<>();

        for (RealEstate realEstate : realEstates) {
            int numOfAlarms = 0;
            for (Device device : realEstate.getDevices()) {
                numOfAlarms += alarmRepository.getAlarmByDevice(device.getId(), startTime, endTime).size();
            }
            responses.add(new ReportResponse(realEstate.getId(), realEstate.getName(), numOfAlarms));
        }

        return responses;
    }

    private void checkDateData(LocalDateTime startTime, LocalDateTime endTime) throws CannotPerformActionException {
        if (startTime.isAfter(endTime)) {
            throw new CannotPerformActionException("Start time is after end time.");
        }
    }

    private List<AlarmResponse> showAlarmsForCertainDevice(RealEstate realEstate, Long deviceId, LocalDateTime bottomDate) {
        List<AlarmResponse> alarmResponses = new LinkedList<>();
        for (Device device : realEstate.getDevices()) {
            if (Objects.equals(device.getId(), deviceId)) {
                alarmResponses = fromAlarmsToResponses(alarmRepository.getFilteredAlarms(device.getId(), bottomDate), device.getName());
                break;
            }
        }

        return alarmResponses;
    }

    private List<AlarmResponse> showAlarmsForAllDevices(RealEstate realEstate, LocalDateTime bottomDate) {
        List<AlarmResponse> alarmResponses = new LinkedList<>();
        for (Device device : realEstate.getDevices()) {
            alarmResponses = Stream.concat(alarmResponses.stream(), fromAlarmsToResponses(alarmRepository.getFilteredAlarms(device.getId(), bottomDate), device.getName()).stream()).toList();
        }

        return alarmResponses;
    }

    private LocalDateTime getBottomTime(int time) throws CannotPerformActionException {
        switch (time) {
            case LAST_7_DAYS: return LocalDateTime.now().minusDays(LAST_7_DAYS);
            case LAST_30_DAYS: return LocalDateTime.now().minusDays(LAST_30_DAYS);
            case SHOW_ALL: return LocalDateTime.now().minusDays(DAYS_IN_YEAR);
            default: throw new CannotPerformActionException("Invalid time");
        }

    }
}
