package com.ftn.security.smarthomebackend.service.implementation;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.enums.Role;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.model.*;
import com.ftn.security.smarthomebackend.security.FingerprintProperties;
import com.ftn.security.smarthomebackend.security.FingerprintUtils;
import com.ftn.security.smarthomebackend.security.JWTUtils;
import com.ftn.security.smarthomebackend.security.UserPrinciple;
import com.ftn.security.smarthomebackend.service.interfaces.IAlarmService;
import com.ftn.security.smarthomebackend.service.interfaces.IAuthService;
import com.ftn.security.smarthomebackend.service.interfaces.ILogService;
import com.ftn.security.smarthomebackend.service.interfaces.IUserService;
import com.ftn.security.smarthomebackend.util.LogGenerator;
import com.ftn.security.smarthomebackend.util.MaliciousAdresses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.ftn.security.smarthomebackend.util.Constants.MAX_NUM_VERIFY_TRIES;
import static com.ftn.security.smarthomebackend.util.Helper.generateSecurityCode;
import static com.ftn.security.smarthomebackend.util.Helper.getHash;

@Service

public class AuthService implements IAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ILogService logService;
    @Autowired
    private IAlarmService alarmService;

    private KieContainer kieContainer;
    @Autowired
    public AuthService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
    }

    @Override
    public LoginResponse loginAdmin(final String email, final String password, final HttpServletRequest request, final HttpServletResponse response) throws UserLockedException, InvalidCredsException, EntityNotFoundException {
        return loginProcess(email, password, request, true, response);
    }

    @Override
    public LoginResponse loginRegularUser(final String email, final String password, final HttpServletRequest request, final HttpServletResponse response) throws UserLockedException, InvalidCredsException, EntityNotFoundException {
        return loginProcess(email, password, request, false, response);
    }

    @Override
    public void logout(HttpServletRequest request) {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        try {
            String email = JWTUtils.extractEmailFromJWT(jwt);
            User usr = userService.getVerifiedUser(email);
            usr.setFailedAttempts(0);
            userService.updateUsersJWTBlacklist(usr, new BlacklistedJWT(JWTUtils.extractTokenFromJWT(jwt), usr));
        } catch (InvalidJWTException | EntityNotFoundException ignored) {
        }
    }

    @Override
    public void generatePin(String email) throws EntityNotFoundException, IOException, MailCannotBeSentException {
        User user = userService.getVerifiedUser(email);
        user.setFailedAttempts(0);
        String pin = String.valueOf(generateSecurityCode());
        String hashPin = getHash(pin);
        user.setPin(hashPin);
        userService.save(user);
        sendPinEmail(pin);
    }

    @Override
    public void confirmPin(String email, String pin)
            throws EntityNotFoundException, WrongVerifyTryException, UserLockedException
    {
        User user = userService.getVerifiedUser(email);

        if (!checkPin(pin, user.getPin())) {
            incrementFailedAttempts(user.getEmail());
            logService.generateLog(LogGenerator.unuccessfulLogin(email), LogLevel.ERROR);
            throw new WrongVerifyTryException("Your security code is not accepted. Try again.");
        }
    }

    @Override
    public void incrementFailedAttempts(String email) throws EntityNotFoundException, UserLockedException {
        User user = userService.getVerifiedUser(email);
        this.checkUserLocked(user.getLockedUntil(), email);

        user.setFailedAttempts(user.getFailedAttempts()+1);
        if(user.getFailedAttempts() == MAX_NUM_VERIFY_TRIES){
            user.setLockedUntil((LocalDateTime.now()).plusDays(1));
            fireRules(user);
            userService.save(user);
            logService.generateLog(LogGenerator.accountLockedLogin(email), LogLevel.ERROR);

            throw new UserLockedException("Your account is locked. You can login again in 24 hours.");
        }

        userService.save(user);
    }

    private boolean checkMaliciousAddress(String address){
        KieSession kieSession = kieContainer.newKieSession("smartSession");
        kieSession.getAgenda().getAgendaGroup("admin_alarms").setFocus();
        List<String> maliciousIpAddresses = MaliciousAdresses.readMaliciousAddresses();

        kieSession.insert(address);
        kieSession.insert(maliciousIpAddresses);

        kieSession.fireAllRules();


        Collection<Alarm> results = (Collection<Alarm>) kieSession.getObjects(new ClassObjectFilter(Alarm.class));
        if(results.size() != 0){
            alarmService.saveAll(results.stream().toList());
            return true;
        }

        return false;
    }

    private void fireRules(User user){
        KieSession kieSession = kieContainer.newKieSession("smartSession");
        kieSession.getAgenda().getAgendaGroup("admin_alarms").setFocus();

        kieSession.insert(user);

        kieSession.fireAllRules();

        Collection<Alarm> results = (Collection<Alarm>) kieSession.getObjects(new ClassObjectFilter(Alarm.class));
        if(results.size() != 0){
            alarmService.saveAll(results.stream().toList());
        }
    }

    private boolean checkPin(String enteredPin, String pin){
        return BCrypt.checkpw(enteredPin, pin);
    }

    private void sendPinEmail(String pin) throws IOException, MailCannotBeSentException {
        this.emailService.sendPinCodeEmail(pin);
    }

    private LoginResponse loginProcess(final String email, final String password, final HttpServletRequest request, final boolean isAdmin, final HttpServletResponse response)
            throws InvalidCredsException, UserLockedException, EntityNotFoundException {
        Authentication authenticate;

        if(checkMaliciousAddress(request.getRemoteAddr())){
            logService.generateLog(LogGenerator.successfulLogin(email), LogLevel.ERROR);
        }

        try {

            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            logService.generateLog(LogGenerator.successfulLogin(email), LogLevel.INFO);
        } catch (Exception ignored) {
            incrementFailedAttempts(email);
            logService.generateLog(LogGenerator.badCredentialLogin(email), LogLevel.ERROR);
            throw new InvalidCredsException("Invalid creds!");
        }

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrinciple userPrinciple = (UserPrinciple) authenticate.getPrincipal();
        UserResponse userResponse = userPrinciple.getUser();

        this.checkInvalidRole(isAdmin, userResponse);
        this.checkUserLocked(userResponse.getLockedUntil(), userResponse.getEmail());

        String rawFingerprint = createCookie(response);

        try {
            userService.removeExpiredJWTsFromUserBlacklist(userService.getVerifiedUser(email));
        } catch (EntityNotFoundException ignored) {}

        return new LoginResponse(JWTUtils.generateJWT(email, rawFingerprint), userResponse);
    }

    private void checkInvalidRole(boolean isAdmin, UserResponse userResponse)
            throws EntityNotFoundException, InvalidCredsException, UserLockedException
    {
        if (isAdmin != userResponse.getRole().getRoleName().equals("ROLE_ADMIN")) {
            incrementFailedAttempts(userResponse.getEmail());
            logService.generateLog(LogGenerator.unuccessfulLogin(userResponse.getEmail()), LogLevel.ERROR);
            throw new InvalidCredsException("Invalid creds!");
        }
    }

    private void checkUserLocked(LocalDateTime lockedUntil, String email) throws UserLockedException {
        if(lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now())){
            logService.generateLog(LogGenerator.accountLockedLogin(email), LogLevel.ERROR);
            throw new UserLockedException("Your account is locked.");
        }
    }

    private String createCookie(HttpServletResponse response) {
        String rawFingerprint = FingerprintUtils.generateRandomRawFingerprint();

        Cookie cookie = new Cookie(FingerprintProperties.FINGERPRINT_COOKIE, rawFingerprint);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(3600*4);
        response.addCookie(cookie);

        return rawFingerprint;
    }

}
