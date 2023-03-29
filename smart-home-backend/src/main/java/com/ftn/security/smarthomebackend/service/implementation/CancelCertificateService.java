package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.CancelCertificate;
import com.ftn.security.smarthomebackend.repository.CancelCertificateRepository;
import com.ftn.security.smarthomebackend.service.interfaces.ICancelCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CancelCertificateService implements ICancelCertificateService {

    @Autowired
    private CancelCertificateRepository cancelCertificateRepository;

    public CancelCertificate getMostRecentByAlias(String alias) throws EntityNotFoundException {

        return cancelCertificateRepository.findMostRecentByAlias(alias)
                .orElseThrow(() -> new EntityNotFoundException(alias, EntityType.CERTIFICATE));
    }

    public boolean mostRecentCancelledAliasExists(String alias) {

        return cancelCertificateRepository.findMostRecentByAlias(alias).isPresent();
    }

    public void cancelCertificate(String alias, String reason) {
        Optional<CancelCertificate> existedCancellation = cancelCertificateRepository.findByAlias(alias);
        if(existedCancellation.isPresent()){
            existedCancellation.get().setMostRecent(false);
            cancelCertificateRepository.save(existedCancellation.get());
        }

        CancelCertificate cancelCertificate = new CancelCertificate(alias, reason);
        cancelCertificateRepository.save(cancelCertificate);
    }

}
