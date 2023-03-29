package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.CancelCertificate;

public interface ICancelCertificateService {
    CancelCertificate getMostRecentByAlias(String alias) throws EntityNotFoundException;
    void cancelCertificate(String alias, String reason);
    boolean mostRecentCancelledAliasExists(String alias);
}
