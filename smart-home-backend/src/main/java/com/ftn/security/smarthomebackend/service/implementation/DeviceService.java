package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.DevSimDataResponse;
import com.ftn.security.smarthomebackend.dto.response.DeviceResponse;
import com.ftn.security.smarthomebackend.enums.DeviceType;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.Device;
import com.ftn.security.smarthomebackend.model.RealEstate;
import com.ftn.security.smarthomebackend.repository.DeviceRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IDeviceService;
import com.ftn.security.smarthomebackend.service.interfaces.IRealEstateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static com.ftn.security.smarthomebackend.dto.response.DeviceResponse.fromDevicesToResponses;

@Service
public class DeviceService implements IDeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IRealEstateService realEstateService;

    @Override
    public Device getDeviceById(Long id) throws EntityNotFoundException {
        return deviceRepository.getDeviceById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.DEVICE));
    }

    @Override
    public List<DeviceResponse> getDevicesPerRealEstate(Long id) {

        return fromDevicesToResponses(deviceRepository.getDevicesPerRealEstate(id));
    }

    @Override
    public boolean deleteDevice(Long id) throws EntityNotFoundException {
        Device device = getDeviceById(id);
        deviceRepository.delete(device);

        return true;
    }

    @Override
    public DeviceResponse updateDevice(Long id, DeviceType deviceType, String name, String filterRegex, int periodRead) throws EntityNotFoundException {
        Device device = getDeviceById(id);
        device.setName(name);
        device.setDeviceType(deviceType);
        device.setFilterRegex(filterRegex);
        device.setPeriodRead(periodRead);
        device.setPhotoPath(getPhotoPathByDeviceType(deviceType));

        return new DeviceResponse(deviceRepository.save(device));
    }

    @Override
    public DeviceResponse createDevice(DeviceType deviceType, String name, String filterRegex, int periodRead, Long realEstateId) throws EntityNotFoundException, CannotPerformActionException {
        if (realEstateId == null) {
            throw new CannotPerformActionException("Real estate id cannot be empty.");
        }

        RealEstate realEstate = realEstateService.getRealEstateById(realEstateId);
        Device device = new Device(deviceType, name, filterRegex, periodRead, realEstate, getPhotoPathByDeviceType(deviceType));

        return new DeviceResponse(deviceRepository.save(device));
    }

    @Override
    public List<DevSimDataResponse> getSimulationData() {
        return deviceRepository.findAll().stream().
                map(d -> new DevSimDataResponse(d.getId(), d.getDeviceType()))
                .collect(Collectors.toList());
    }

    private static String getPhotoPathByDeviceType(DeviceType deviceType) {
        switch (deviceType){
            case CAMERA -> { return "camera.png"; }
            case AIR_CONDITIONER -> { return "air-conditioner.png"; }
            case SMOKE_SENSOR -> { return "smoke-sensor.png"; }
            case TEMP_SENSOR -> { return "temperature-sensor.png"; }
            case WATER_SENSOR -> { return "water-sensor.png"; }
            case WIN_LOCKED_SENSOR -> { return "win-locked-sensor.png"; }
            case AIR_SENSOR -> { return "air-sensor.png"; }
            default -> { return ""; }
        }
    }
}
