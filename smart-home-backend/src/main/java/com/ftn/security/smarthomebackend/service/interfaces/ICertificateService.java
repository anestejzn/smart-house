package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.request.NewCertificateRequest;
import com.ftn.security.smarthomebackend.dto.response.CertificateResponse;
import com.ftn.security.smarthomebackend.dto.response.SortedAliasesResponse;
import com.ftn.security.smarthomebackend.enums.CertificateSortType;
import com.ftn.security.smarthomebackend.enums.CertificateValidityType;
import com.ftn.security.smarthomebackend.exception.*;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.List;


public interface ICertificateService {
    void createAndSaveRootCertificate() throws KeyStoreMalfunctionedException;
    void createAndSaveIntermediateCertificate(final String alias) throws KeyStoreMalfunctionedException, AliasDoesNotExistException;
    void createAndSaveLeafCertificate(NewCertificateRequest certRequest) throws EntityNotFoundException, AliasAlreadyExistsException, KeyStoreCertificateException, InvalidKeyUsagesComboException, CertificateParsingException, CertificateEncodingException, KeyStoreException, MailCannotBeSentException, MessagingException, IOException, KeyStoreMalfunctionedException, AliasDoesNotExistException;
    List<SortedAliasesResponse> getAliases(CertificateSortType type, CertificateValidityType validity) throws KeyStoreCertificateException, EntityNotFoundException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificateException, KeyStoreMalfunctionedException;
    List<CertificateResponse> getCertificateByAlias(String alias) throws KeyStoreCertificateException, EntityNotFoundException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidCertificateException, KeyStoreMalfunctionedException;
    boolean cancelCertificate(String alias, String reason) throws EntityNotFoundException, AliasAlreadyExistsException, AliasDoesNotExistException, KeyStoreMalfunctionedException;
}
