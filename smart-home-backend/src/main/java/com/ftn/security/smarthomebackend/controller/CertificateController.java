package com.ftn.security.smarthomebackend.controller;


import com.ftn.security.smarthomebackend.service.interfaces.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private ICertificateService certificateService;

    @PostMapping("/create/root")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRootCertificate() {
        certificateService.createAndWriteRootCertificate();
    }

    @PostMapping("/create/intermediate")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIntermediateCertificate() {
        certificateService.createAndWriteIntermediateCertificate();
    }

}
