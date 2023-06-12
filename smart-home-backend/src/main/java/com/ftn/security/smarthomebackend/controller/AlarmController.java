package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.response.AlarmResponse;
import com.ftn.security.smarthomebackend.exception.CannotPerformActionException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.interfaces.IAlarmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ftn.security.smarthomebackend.util.Constants.MISSING_ID;

@RestController
@RequestMapping("alarms")
public class AlarmController {

    @Autowired
    private IAlarmService alarmService;

    @GetMapping("/{realEstateId}/{deviceId}/{time}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('GET_FILTERED_ALARMS')")
    public List<AlarmResponse> getFilteredAlarms(@PathVariable @Valid @NotNull(message = MISSING_ID) @Positive(message = MISSING_ID) Long realEstateId,
                                                 @PathVariable @Valid @NotNull(message = MISSING_ID) Long deviceId,
                                                 @PathVariable @Valid @NotNull(message = "Time is missing") int time
    ) throws EntityNotFoundException, CannotPerformActionException {

        return alarmService.getFilteredAlarms(
            realEstateId,
            deviceId,
            time
        );
    }

}
