package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.DevSimDataResponse;
import com.ftn.security.smarthomebackend.dto.response.DeviceResponse;
import com.ftn.security.smarthomebackend.enums.DeviceType;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.Device;

import java.util.List;


public interface IDeviceService {

    List<DeviceResponse> getDevicesPerRealEstate(Long id);
    Device getDeviceById(Long id) throws EntityNotFoundException;
    boolean deleteDevice(Long id) throws EntityNotFoundException;

    DeviceResponse updateDevice(Long id, DeviceType deviceType, String name, String filterRegex, int periodRead) throws EntityNotFoundException;

    DeviceResponse createDevice(DeviceType deviceType, String name, String filterRegex, int periodRead, Long realEstateId) throws EntityNotFoundException, CannotPerformActionException;

    List<DevSimDataResponse> getSimulationData();
}
