package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.Device;

public interface IDeviceService {

    Device getById(Long id) throws EntityNotFoundException;
}
