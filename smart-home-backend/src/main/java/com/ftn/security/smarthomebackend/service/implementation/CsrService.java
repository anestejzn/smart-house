package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.CsrResponse;
import com.ftn.security.smarthomebackend.enums.AccountStatus;
import com.ftn.security.smarthomebackend.enums.CSRStatus;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.model.CSR;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.repository.CsrRepository;
import com.ftn.security.smarthomebackend.service.WebSocketService;
import com.ftn.security.smarthomebackend.service.interfaces.ICsrService;
import com.ftn.security.smarthomebackend.service.interfaces.ILogService;
import com.ftn.security.smarthomebackend.service.interfaces.IUserService;
import com.ftn.security.smarthomebackend.util.LogGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ftn.security.smarthomebackend.dto.response.CsrResponse.transformToResponse;
import static com.ftn.security.smarthomebackend.util.CsrConstants.*;

@Service
public class CsrService implements ICsrService {
    @Autowired
    private CsrRepository csrRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private ILogService logService;

    @Override
    public void createCSR(String userEmail) throws EntityNotFoundException {

        User user = userService.getVerifiedUser(userEmail);

        CSR csr = new CSR(
            user, COMMON_NAME, ORGANIZATION_UNIT, ORGANIZATION, CITY, STATE, COUNTRY, CSRStatus.PENDING
        );
        csrRepository.save(csr);

        user.setStatus(AccountStatus.NON_CERTIFICATED);
        userService.save(user);
        logService.generateLog(LogGenerator.createdCsr(userEmail), LogLevel.INFO);
    }

    @Override
    public List<CsrResponse> getPendingCsrs() {

        return transformToResponse(csrRepository.findByPendingStatus());
    }

    @Override
    public CsrResponse rejectCSR(Long id) throws EntityNotFoundException {
        CSR csr = csrRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, EntityType.CSR));
        csr.setStatus(CSRStatus.REJECTED);


        User user = csr.getUser();
        user.setStatus(AccountStatus.REJECTED_CSR);
        userService.save(user);

        csr = csrRepository.save(csr);
        webSocketService.sendMessageAboutRejectCsr(user.getEmail(), "Admin didn't accept your csr.");
        logService.generateLog(LogGenerator.rejectedCsr(user.getEmail()), LogLevel.INFO);
        return new CsrResponse(csr);
    }

    @Override
    public CSR getById(Long id) throws EntityNotFoundException {
        return csrRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, EntityType.CSR));
    }

    @Override
    public void deleteById(Long id) {
        csrRepository.deleteById(id);
    }


}
