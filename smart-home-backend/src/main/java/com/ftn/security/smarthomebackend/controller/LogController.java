package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.CSRRequest;
import com.ftn.security.smarthomebackend.dto.request.LogFilterRequest;
import com.ftn.security.smarthomebackend.dto.response.LogResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.interfaces.ICsrService;
import com.ftn.security.smarthomebackend.service.interfaces.ILogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private ILogService logService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ_LOGS')")
    public List<LogResponse> getAllLogs(){
        return logService.getAllLogs();
    }

    @PutMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('FILTER_LOGS')")
    public List<LogResponse> getFilterLogs(@RequestBody LogFilterRequest logFilterRequest){
        return logService.getFilterLogs(logFilterRequest.getRegex(), logFilterRequest.getDateTime(), logFilterRequest.getLogLevel());
    }
}
