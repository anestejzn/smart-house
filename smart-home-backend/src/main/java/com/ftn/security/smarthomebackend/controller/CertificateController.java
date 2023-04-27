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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.Collections;
import java.util.List;

import static com.ftn.security.smarthomebackend.util.CertificateConstants.INTERMEDIATE_CERT_ALIASES;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private ICertificateService certificateService;

    @GetMapping(value = "aliases/{type}/{validity}")
    @ResponseStatus(HttpStatus.OK)
    public List<SortedAliasesResponse> getAllAliases(@PathVariable @Valid CertificateSortType type,
                                                     @PathVariable @Valid CertificateValidityType validity
    ) throws KeyStoreCertificateException, EntityNotFoundException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificateException, KeyStoreMalfunctionedException {

        return certificateService.getAliases(type, validity);
    }

    @GetMapping(value = "/{alias}")
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateResponse> getCertificateByAlias(@PathVariable String alias) throws KeyStoreCertificateException, EntityNotFoundException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificateException, KeyStoreMalfunctionedException {
        return certificateService.getCertificateByAlias(alias);
    }

    @PostMapping("/create/root")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRootCertificate() throws KeyStoreMalfunctionedException {
        certificateService.createAndSaveRootCertificate();
    }

    @PostMapping("/create/intermediates")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIntermediateCertificate() throws KeyStoreMalfunctionedException, AliasDoesNotExistException {
        for (String intermediate_alias : INTERMEDIATE_CERT_ALIASES)
            certificateService.createAndSaveIntermediateCertificate(intermediate_alias);
    }

    @PostMapping("/create/leaf")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void createLeafCertificate(@Valid @RequestBody final NewCertificateRequest certRequest)
            throws EntityNotFoundException, AliasAlreadyExistsException, KeyStoreCertificateException, InvalidKeyUsagesComboException, CertificateParsingException, CertificateEncodingException, KeyStoreException, MailCannotBeSentException, MessagingException, IOException, KeyStoreMalfunctionedException, AliasDoesNotExistException {
        certificateService.createAndSaveLeafCertificate(certRequest);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public boolean cancelCertificate(@Valid @RequestBody final CancelCertificateRequest request) throws AliasAlreadyExistsException, EntityNotFoundException, AliasDoesNotExistException, KeyStoreMalfunctionedException {
        return certificateService.cancelCertificate(request.getAlias(), request.getCancelReason());
    }

}
