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
    void createAndSaveRootCertificate() throws KeyStoreMalfunctionedException, InvalidCertificateException, CertificateParsingException;
    void createAndSaveIntermediateCertificate(final String alias) throws KeyStoreMalfunctionedException, AliasDoesNotExistException, InvalidCertificateException, CertificateParsingException;
    void createAndSaveLeafCertificate(final NewCertificateRequest certRequest) throws EntityNotFoundException, AliasDoesNotExistException, KeyStoreMalfunctionedException, AliasAlreadyExistsException, InvalidCertificateException, KeyStoreCertificateException, CertificateParsingException, MessagingException, CertificateEncodingException, KeyStoreException, MailCannotBeSentException, IOException;
    List<SortedAliasesResponse> getAliasesByFilters(final CertificateSortType type, final CertificateValidityType validity) throws KeyStoreMalfunctionedException;
    List<CertificateResponse> getCertificateByAlias(final String alias) throws KeyStoreCertificateException, KeyStoreMalfunctionedException;
    boolean cancelCertificate(final String alias, final String reason) throws EntityNotFoundException, KeyStoreMalfunctionedException;
}