package com.ftn.security.smarthomebackend.controller;


import com.ftn.security.smarthomebackend.dto.request.CancelCertificateRequest;
import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
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

    @GetMapping(value = "aliases")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllAliases() throws KeyStoreCertificateException {

        return certificateService.getAliases();
    }

    @GetMapping(value = "/{alias}")
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateResponse> getCertificateByAlias(@PathVariable String alias) throws KeyStoreCertificateException {

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
    public void createLeafCertificate(@Valid @RequestBody final NewCertificateRequest certRequest) throws EntityNotFoundException, AliasAlreadyExistsException {
        certificateService.createAndSaveLeafCertificate(certRequest);
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void cancelCertificate(@Valid @RequestBody final CancelCertificateRequest request) throws AliasAlreadyExistsException, EntityNotFoundException {
        certificateService.cancelCertificate(request.getAlias(), request.getCancelReason());
    }

}
