package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.DeviceRequest;
import com.ftn.security.smarthomebackend.dto.response.DeviceResponse;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.interfaces.IDeviceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ftn.security.smarthomebackend.util.Constants.MISSING_ID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;

    @GetMapping("/devices-per-real-estate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ_DEVICES')")
    public List<DeviceResponse> getDevicesPerRealEstate(@PathVariable @Valid @NotNull(message = MISSING_ID) Long id) {

        return deviceService.getDevicesPerRealEstate(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('EDIT_DEVICES')")
    public DeviceResponse updateDevice(@PathVariable @Valid @NotNull(message = MISSING_ID) Long id, @Valid @RequestBody DeviceRequest request)
            throws EntityNotFoundException
    {

        return deviceService.updateDevice(id,
                                          request.getDeviceType(),
                                          request.getName(),
                                          request.getFilterRegex(),
                                          request.getPeriodRead()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CREATE_DEVICES')")
    public DeviceResponse createDevice(@Valid @RequestBody DeviceRequest request)
            throws EntityNotFoundException, CannotPerformActionException {

        return deviceService.createDevice(
                request.getDeviceType(),
                request.getName(),
                request.getFilterRegex(),
                request.getPeriodRead(),
                request.getRealEstateId()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('DELETE_DEVICE')")
    public boolean deleteDevice(@PathVariable @Valid @NotNull(message = MISSING_ID) Long id)
            throws EntityNotFoundException
    {

        return deviceService.deleteDevice(id);
    }

}
