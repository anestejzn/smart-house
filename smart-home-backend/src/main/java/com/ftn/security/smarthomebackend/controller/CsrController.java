package com.ftn.security.smarthomebackend.controller;

import com.ftn.security.smarthomebackend.dto.request.CSRRequest;
import com.ftn.security.smarthomebackend.dto.response.CsrResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.interfaces.ICsrService;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/csr")
public class CsrController {

    @Autowired
    private ICsrService csrService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_CSR')")
    public void createCsr(@Valid @RequestBody final CSRRequest csrRequest) throws EntityNotFoundException {
        csrService.createCSR(csrRequest.getEmail());
    }

    @GetMapping("/pending")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('GET_PENDING_CSRS')")
    public List<CsrResponse> getPendingCsrs(){
        return csrService.getPendingCsrs();
    }


    @PostMapping("/reject")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('REJECT_CSR')")
    public CsrResponse rejectCsr(@Valid @NotNull @PositiveOrZero @RequestBody Long id) throws EntityNotFoundException {
        return csrService.rejectCSR(id);
    }
}
