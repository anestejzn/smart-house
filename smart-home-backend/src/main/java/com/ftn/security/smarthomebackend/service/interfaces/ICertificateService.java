package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.dto.response.SortedAliasesResponse;
import com.ftn.security.smarthomebackend.enums.CertificateSortType;
import com.ftn.security.smarthomebackend.enums.CertificateValidityType;
import com.ftn.security.smarthomebackend.exception.AliasAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.InvalidKeyUsagesComboException;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;

import java.util.List;


public interface ICertificateService {
    void createAndSaveRootCertificate();
    void createAndSaveIntermediateCertificate();
    void createAndSaveLeafCertificate(NewCertificateRequest certRequest) throws EntityNotFoundException, AliasAlreadyExistsException, KeyStoreCertificateException, InvalidKeyUsagesComboException;
    List<SortedAliasesResponse> getAliases(CertificateSortType type, CertificateValidityType validity) throws KeyStoreCertificateException, EntityNotFoundException;
    List<CertificateResponse> getCertificateByAlias(String alias) throws KeyStoreCertificateException, EntityNotFoundException;
    boolean cancelCertificate(String alias, String reason) throws EntityNotFoundException, AliasAlreadyExistsException;
    boolean validateCertificate(String alias) throws EntityNotFoundException;
}
