package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.response.AlarmResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.implementation.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alarm")
@Validated
public class AlarmController {
    @Autowired
    private AlarmService alarmService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('GET_ALL_ALARMS')")
    public List<AlarmResponse> getAllAlarms() throws EntityNotFoundException {
        return alarmService.getAllAlarms();
    }
}
