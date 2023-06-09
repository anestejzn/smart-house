package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.CancelCertificate;
import com.ftn.security.smarthomebackend.repository.CancelCertificateRepository;
import com.ftn.security.smarthomebackend.service.WebSocketService;
import com.ftn.security.smarthomebackend.service.interfaces.ICancelCertificateService;
import com.ftn.security.smarthomebackend.service.interfaces.ILogService;
import com.ftn.security.smarthomebackend.util.LogGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CancelCertificateService implements ICancelCertificateService {

    @Autowired
    private CancelCertificateRepository cancelCertificateRepository;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private ILogService logService;

    public CancelCertificate getMostRecentByAlias(String alias) throws EntityNotFoundException {

        if(cancelCertificateRepository.findMostRecentByAlias(alias).isPresent()){
            return cancelCertificateRepository.findMostRecentByAlias(alias).get();
        }
        else{
            logService.generateLog(LogGenerator.notFoundCertificate(alias), LogLevel.INFO);
            throw new EntityNotFoundException(alias, EntityType.CERTIFICATE);
        }
    }

    public boolean mostRecentCancelledAliasExists(String alias) {

        return cancelCertificateRepository.findMostRecentByAlias(alias).isPresent();
    }

    public void cancelCertificate(String alias, String reason) {
        //sta je ovde uradjeno???? proveriii!!
        Optional<CancelCertificate> existedCancellation = cancelCertificateRepository.findByAlias(alias);
        if(existedCancellation.isPresent()){
            existedCancellation.get().setMostRecent(false);
            cancelCertificateRepository.save(existedCancellation.get());
        }

        CancelCertificate cancelCertificate = new CancelCertificate(alias, reason);
        cancelCertificateRepository.save(cancelCertificate);
        webSocketService.sendMessageAboutCancelCertificate(alias, reason);
    }

}
