package com.ftn.security.smarthomebackend.service.interfaces;

import com.ftn.security.smarthomebackend.dto.response.CsrResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.CSR;

import java.util.List;

public interface ICsrService {

    void createCSR(String userEmail) throws EntityNotFoundException;

    List<CsrResponse> getPendingCsrs();

    CsrResponse rejectCSR(Long id) throws EntityNotFoundException;
    CSR getById(Long id) throws EntityNotFoundException;
    void deleteById(Long id);
}
