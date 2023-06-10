package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.DeviceResponse;
import com.ftn.security.smarthomebackend.repository.DeviceRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ftn.security.smarthomebackend.dto.response.DeviceResponse.fromDevicesToResponses;

@Service
public class DeviceService implements IDeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<DeviceResponse> getDevicesPerRealEstate(Long id) {

        return fromDevicesToResponses(deviceRepository.getDevicesPerRealEstate(id));
    }
}
