package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.CSRRequest;
import com.ftn.security.smarthomebackend.dto.response.CsrResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.implementation.CsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/csr")
public class CsrController {

    @Autowired
    private CsrService csrService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCsr(@Valid @RequestBody final CSRRequest csrRequest) throws EntityNotFoundException {
        csrService.createCSR(csrRequest.getEmail());
    }

    @GetMapping("/pending")
    @ResponseStatus(HttpStatus.OK)
    public List<CsrResponse> getPendingCsrs(){
        return csrService.getPendingCsrs();
    }


    @PostMapping("/reject")
    @ResponseStatus(HttpStatus.OK)
    public CsrResponse rejectCsr(@Valid @NotNull @RequestBody Long id) throws EntityNotFoundException {
        return csrService.rejectCSR(id);
    }
}
