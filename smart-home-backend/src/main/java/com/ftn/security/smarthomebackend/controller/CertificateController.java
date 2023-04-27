package com.ftn.security.smarthomebackend.controller;


import com.ftn.security.smarthomebackend.dto.request.CancelCertificateRequest;
import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.dto.response.SortedAliasesResponse;
import com.ftn.security.smarthomebackend.enums.CertificateSortType;
import com.ftn.security.smarthomebackend.enums.CertificateValidityType;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.service.interfaces.ICertificateService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.List;

import static com.ftn.security.smarthomebackend.util.CertificateConstants.INTERMEDIATE_CERT_ALIASES;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private ICertificateService certificateService;

    @GetMapping(value = "aliases/{type}/{validity}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<SortedAliasesResponse> getAllAliases(@PathVariable @Valid CertificateSortType type, @PathVariable @Valid CertificateValidityType validity) throws KeyStoreMalfunctionedException {
        return certificateService.getAliasesByFilters(type, validity);
    }

    @GetMapping(value = "/{alias}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<CertificateResponse> getCertificateByAlias(@PathVariable String alias) throws KeyStoreCertificateException, KeyStoreMalfunctionedException {
        return certificateService.getCertificateByAlias(alias);
    }

    @PostMapping("/create/root")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRootCertificate() throws KeyStoreMalfunctionedException, CertificateParsingException, InvalidCertificateException {
        certificateService.createAndSaveRootCertificate();
    }

    @PostMapping("/create/intermediates")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIntermediateCertificate() throws KeyStoreMalfunctionedException, AliasDoesNotExistException, CertificateParsingException, InvalidCertificateException {
        for (String intermediate_alias : INTERMEDIATE_CERT_ALIASES)
            certificateService.createAndSaveIntermediateCertificate(intermediate_alias);
    }

    @PostMapping("/create/leaf")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void createLeafCertificate(@Valid @RequestBody final NewCertificateRequest certRequest) throws AliasDoesNotExistException, KeyStoreCertificateException, CertificateParsingException, MessagingException, AliasAlreadyExistsException, CertificateEncodingException, EntityNotFoundException, KeyStoreException, MailCannotBeSentException, IOException, InvalidCertificateException, KeyStoreMalfunctionedException {
        certificateService.createAndSaveLeafCertificate(certRequest);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public boolean cancelCertificate(@Valid @RequestBody final CancelCertificateRequest request) throws EntityNotFoundException, KeyStoreMalfunctionedException {
        return certificateService.cancelCertificate(request.getAlias(), request.getCancelReason());
    }

}
