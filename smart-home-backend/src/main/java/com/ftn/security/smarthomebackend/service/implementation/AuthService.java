package com.ftn.security.smarthomebackend.service.implementation;

import com.auth0.jwt.JWT;
import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.MailCannotBeSentException;
import com.ftn.security.smarthomebackend.exception.UserLockedException;
import com.ftn.security.smarthomebackend.exception.WrongVerifyTryException;
import com.ftn.security.smarthomebackend.model.RegularUser;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.security.JwtProperties;
import com.ftn.security.smarthomebackend.security.UserPrinciple;
import com.ftn.security.smarthomebackend.service.interfaces.IAuthService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.ftn.security.smarthomebackend.util.Helper.generateSecurityCode;
import static com.ftn.security.smarthomebackend.util.Helper.getHash;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Override
    public LoginResponse login(String email, String password) throws UserLockedException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        final String token = generateToken(email);
        UserPrinciple userPrinciple = (UserPrinciple) authenticate.getPrincipal();
        UserResponse userResponse = userPrinciple.getUser();

        if(userResponse.getLockedUntil() != null && userResponse.getLockedUntil().isAfter(LocalDateTime.now())){
            throw new UserLockedException("Your account is locked.");
        }

        return new LoginResponse(token, userResponse);
    }

    @Override
    public void generatePin(String email) throws EntityNotFoundException, IOException, MailCannotBeSentException {
        RegularUser user = (RegularUser) userService.getVerifiedUser(email);
        user.setFailedAttempts(0);
        String pin = String.valueOf(generateSecurityCode());
        String hashPin = getHash(pin);
        user.setPin(hashPin);
        userService.save(user);
        sendPinEmail(pin);
    }

    @Override
    public boolean confirmPin(String email, String pin) throws EntityNotFoundException, WrongVerifyTryException {
        RegularUser user = (RegularUser) userService.getVerifiedUser(email);
        if(checkPin(pin, user.getPin())){
            return true;
        }
        else {
            throw new WrongVerifyTryException("Your security code is not accepted. Try again.");
        }
    }

    @Override
    public boolean incrementFailedAttempts(String email) throws EntityNotFoundException {
        User user = userService.getVerifiedUser(email);
        user.setFailedAttempts(user.getFailedAttempts()+1);
        System.out.println(user.getFailedAttempts());
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
    private String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
    }
}
