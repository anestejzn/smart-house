package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.request.CancelCertificateRequest;
import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.exception.AliasAlreadyExistsException;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.InvalidKeyUsagesComboException;
import com.ftn.security.smarthomebackend.exception.KeyStoreCertificateException;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;


public interface ICertificateService {
    void createAndSaveRootCertificate();
    void createAndSaveIntermediateCertificate();
    void createAndSaveLeafCertificate(NewCertificateRequest certRequest) throws EntityNotFoundException, AliasAlreadyExistsException, KeyStoreCertificateException, InvalidKeyUsagesComboException;
    List<String> getAliases() throws KeyStoreCertificateException;
    List<CertificateResponse> getCertificateByAlias(String alias) throws KeyStoreCertificateException;
    void cancelCertificate(String alias, String reason) throws EntityNotFoundException, AliasAlreadyExistsException;
}
