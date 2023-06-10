package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.response.DeviceResponse;
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

}
