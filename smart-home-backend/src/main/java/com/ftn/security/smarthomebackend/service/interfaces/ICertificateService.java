package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.exception.AliasAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;


public interface ICertificateService {
    void createAndSaveRootCertificate();
    void createAndSaveIntermediateCertificate();
    void createAndSaveLeafCertificate(NewCertificateRequest certRequest) throws EntityNotFoundException, AliasAlreadyExistsException;
}
