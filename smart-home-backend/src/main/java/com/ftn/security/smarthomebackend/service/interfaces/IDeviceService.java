package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.DeviceResponse;

import java.util.List;

public interface IDeviceService {

    List<DeviceResponse> getDevicesPerRealEstate(Long id);
}
