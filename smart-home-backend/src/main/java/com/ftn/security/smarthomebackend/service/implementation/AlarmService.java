package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.AlarmResponse;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.Alarm;
import com.ftn.security.smarthomebackend.model.Device;
import com.ftn.security.smarthomebackend.repository.AlarmRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IAlarmService;
import com.ftn.security.smarthomebackend.util.LogGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class AlarmService implements IAlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private DeviceService deviceService;

    @Override
    public void saveAll(List<Alarm> alarms) {
        alarmRepository.saveAll(alarms);
    }

    @Override
    public List<AlarmResponse> getAllAlarms() throws EntityNotFoundException {
        List<Alarm> alarms = alarmRepository.findAll();
        List<AlarmResponse> alarmResponses = new LinkedList<>();
        for(Alarm alarm : alarms){
//            try {
            if(alarm.getDeviceId() == null){
                alarmResponses.add(new AlarmResponse(alarm.getDateTime(), null, alarm.getMessage()));
            }
            else {
                Device device = deviceService.getById(alarm.getDeviceId());
                alarmResponses.add(new AlarmResponse(alarm.getDateTime(), device, alarm.getMessage()));
            }
//            }
//            catch(EntityNotFoundException e){
//                logService.generateLog(LogGenerator.notFoundDevice(alarm.getDeviceId()), LogLevel.ERROR);
//                throw new EntityNotFoundException(alarm.getDeviceId(), EntityType.DEVICE);
//            }
        }

        return alarmResponses;
    }
}
