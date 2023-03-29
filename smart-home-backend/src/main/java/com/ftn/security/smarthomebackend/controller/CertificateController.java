package com.ftn.security.smarthomebackend.controller;


import com.ftn.security.smarthomebackend.dto.request.CancelCertificateRequest;
import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.dto.response.SortedAliasesResponse;
import com.ftn.security.smarthomebackend.enums.CertificateSortType;
import com.ftn.security.smarthomebackend.enums.CertificateValidityType;
import com.ftn.security.smarthomebackend.exception.AliasAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;
import com.ftn.security.smarthomebackend.service.interfaces.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private ICertificateService certificateService;

    @GetMapping(value = "aliases/{type}/{validity}")
    @ResponseStatus(HttpStatus.OK)
    public List<SortedAliasesResponse> getAllAliases(@PathVariable @Valid CertificateSortType type,
                                                     @PathVariable @Valid CertificateValidityType validity
    ) throws KeyStoreCertificateException, EntityNotFoundException {

        return certificateService.getAliases(type, validity);
    }

    @GetMapping(value = "/{alias}")
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateResponse> getCertificateByAlias(@PathVariable String alias) throws KeyStoreCertificateException, EntityNotFoundException {

        return certificateService.getCertificateByAlias(alias);
    }

    @PostMapping("/create/root")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRootCertificate() {
        certificateService.createAndSaveRootCertificate();
    }

    @PostMapping("/create/intermediate")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIntermediateCertificate() {
        certificateService.createAndSaveIntermediateCertificate();
    }

    @PostMapping("/create/leaf")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void createLeafCertificate(@Valid @RequestBody final NewCertificateRequest certRequest)
            throws EntityNotFoundException, AliasAlreadyExistsException, KeyStoreCertificateException
    {
        certificateService.createAndSaveLeafCertificate(certRequest);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public boolean cancelCertificate(@Valid @RequestBody final CancelCertificateRequest request) throws AliasAlreadyExistsException, EntityNotFoundException {
        return certificateService.cancelCertificate(request.getAlias(), request.getCancelReason());
    }

}
