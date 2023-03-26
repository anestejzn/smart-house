package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.dto.response.VerifyMailDTO;
import com.ftn.security.smarthomebackend.enums.EntityType;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.MailCannotBeSentException;
import com.ftn.security.smarthomebackend.exception.WrongVerifyTryException;
import com.ftn.security.smarthomebackend.model.RegistrationVerification;
import com.ftn.security.smarthomebackend.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.ftn.security.smarthomebackend.util.Constants.*;
import static com.ftn.security.smarthomebackend.util.EmailConstants.FRONT_VERIFY_URL;
import static com.ftn.security.smarthomebackend.util.Helper.*;

@Service
public class VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private EmailService emailService;

    public RegistrationVerification get(Long id) throws EntityNotFoundException {

        return verificationRepository.getRegistrationVerificationsById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.VERIFY));
    }

    public RegistrationVerification getByHashedId(String id) throws EntityNotFoundException {

        return verificationRepository.getRegistrationVerificationsByHashedId(id)
                .orElseThrow(() -> new EntityNotFoundException(id, EntityType.VERIFY));
    }

    public RegistrationVerification update(String verifyId, int securityCode) throws EntityNotFoundException, WrongVerifyTryException {
        RegistrationVerification verify = getByHashedId(verifyId);
        if (verify.canVerify(String.valueOf(securityCode))) {
            verify.incrementNumOfTries();
            this.saveChanges(verify, true);

            return verify;
        } else if (verify.wrongCodeButHasTries()){
            this.saveChanges(verify, verify.incrementNumOfTries() >= MAX_NUM_VERIFY_TRIES);

            throw new WrongVerifyTryException("Your security code is not accepted. Try again.");
        } else {
            saveChanges(verify, true);

            throw new WrongVerifyTryException("Your verification code is either expired or typed wrong 3 times. Reset code.");
        }
    }

    private void saveChanges(final RegistrationVerification verify, final boolean used) {
        verify.setUsed(used);
        verificationRepository.save(verify);
    }

    public boolean create(String email) throws IOException, MailCannotBeSentException {
        String salt = generateRandomString(SALT_LENGTH);
        int securityCode = generateSecurityCode();
        RegistrationVerification registrationVerification = new RegistrationVerification(
                getHash(String.valueOf(securityCode)),
                ZERO_FAILED_ATTEMPTS,
                email,
                LocalDateTime.now().plusMinutes(10),
                salt,
                generateHashForURL(salt + email)
        );

        verificationRepository.save(registrationVerification);
        this.sendVerificationEmail(new VerifyMailDTO(securityCode, registrationVerification.getHashedId()));

        return true;
    }

    public void generateNewSecurityCode(String verifyHash)
            throws EntityNotFoundException, IOException, MailCannotBeSentException
    {
        RegistrationVerification verify = getByHashedId(verifyHash);
        create(verify.getUserEmail());
        verificationRepository.delete(verify);
    }

    public void sendVerificationEmail(VerifyMailDTO verifyMailDTO)
            throws IOException, MailCannotBeSentException
    {
        emailService.sendVerificationMail(
                verifyMailDTO.getSecurityCode(),
                String.format("%s%s", FRONT_VERIFY_URL, verifyMailDTO.getHashId())
        );
    }
}
