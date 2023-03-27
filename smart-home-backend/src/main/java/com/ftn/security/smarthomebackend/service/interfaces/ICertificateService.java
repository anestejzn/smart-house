package com.ftn.security.smarthomebackend.service.interfaces;

public interface ICertificateService {
    void createAndWriteRootCertificate();
    void createAndWriteIntermediateCertificate();
}
