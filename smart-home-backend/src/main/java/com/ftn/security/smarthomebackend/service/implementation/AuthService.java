package com.ftn.security.smarthomebackend.service.implementation;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.enums.Role;
import com.ftn.security.smarthomebackend.exception.*;
import com.ftn.security.smarthomebackend.model.BlacklistedJWT;
import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.security.FingerprintProperties;
import com.ftn.security.smarthomebackend.security.FingerprintUtils;
import com.ftn.security.smarthomebackend.security.JWTUtils;
import com.ftn.security.smarthomebackend.security.UserPrinciple;
import com.ftn.security.smarthomebackend.service.interfaces.IAuthService;
import com.ftn.security.smarthomebackend.service.interfaces.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

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

    @Override
    public LoginResponse loginAdmin(final String email, final String password, final HttpServletResponse response) throws UserLockedException, InvalidCredsException {
        return loginProcess(email, password, response, true);
    }

    @Override
    public LoginResponse loginRegularUser(final String email, final String password, final HttpServletResponse response) throws UserLockedException, InvalidCredsException {
        return loginProcess(email, password, response, false);
    }

    @Override
    public void logout(HttpServletRequest request) {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        try {
            String email = JWTUtils.extractEmailFromJWT(jwt);
            User usr = userService.getVerifiedUser(email);
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
    public boolean confirmPin(String email, String pin) throws EntityNotFoundException, WrongVerifyTryException {
        User user = userService.getVerifiedUser(email);
        if (checkPin(pin, user.getPin())) {
            return true;
        }
        else{
            throw new WrongVerifyTryException("Your security code is not accepted. Try again.");
        }
    }

    @Override
    public boolean incrementFailedAttempts(String email) throws EntityNotFoundException {
        User user = userService.getVerifiedUser(email);
        user.setFailedAttempts(user.getFailedAttempts()+1);
        if(user.getFailedAttempts() == 4){
            user.setLockedUntil((LocalDateTime.now()).plusDays(1));
            userService.save(user);
            return false;
        }
        userService.save(user);
        return true;
    }

    private boolean checkPin(String enteredPin, String pin){
        return BCrypt.checkpw(enteredPin, pin);
    }

    private void sendPinEmail(String pin) throws IOException, MailCannotBeSentException {
        this.emailService.sendPinCodeEmail(pin);
    }

    private LoginResponse loginProcess(final String email, final String password, final HttpServletResponse response, final boolean isAdmin)
            throws InvalidCredsException, UserLockedException {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception ignored) {
            throw new InvalidCredsException("Invalid creds!");
        }

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrinciple userPrinciple = (UserPrinciple) authenticate.getPrincipal();
        UserResponse userResponse = userPrinciple.getUser();

        if (isAdmin != userResponse.getRole().getRoleName().equals("ROLE_ADMIN"))
            throw new InvalidCredsException("Invalid creds!");

        if(userResponse.getLockedUntil() != null && userResponse.getLockedUntil().isAfter(LocalDateTime.now())){
            throw new UserLockedException("Your account is locked.");
        }

        String rawFingerprint = FingerprintUtils.generateRandomRawFingerprint();

        Cookie cookie = new Cookie(FingerprintProperties.FINGERPRINT_COOKIE, rawFingerprint);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(3600*4);
        response.addCookie(cookie);

        try {
            userService.removeExpiredJWTsFromUserBlacklist(userService.getVerifiedUser(email));
        } catch (EntityNotFoundException ignored) {}
        return new LoginResponse(JWTUtils.generateJWT(email, rawFingerprint), userResponse);
    }
}
