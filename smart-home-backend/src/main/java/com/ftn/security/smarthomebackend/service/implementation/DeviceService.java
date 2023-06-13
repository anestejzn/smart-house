package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.CancelCertificate;
import com.ftn.security.smarthomebackend.model.Device;
import com.ftn.security.smarthomebackend.repository.DeviceRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IDeviceService;
import com.ftn.security.smarthomebackend.util.LogGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceService implements IDeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Override
    public Device getById(Long id) throws EntityNotFoundException {

        return deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.DEVICE));
    }
}
