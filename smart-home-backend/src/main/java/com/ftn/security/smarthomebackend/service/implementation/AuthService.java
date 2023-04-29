package com.ftn.security.smarthomebackend.service.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftn.security.smarthomebackend.dto.response.LoginResponse;
import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.InvalidCredsException;
import com.ftn.security.smarthomebackend.exception.InvalidJWTException;
import com.ftn.security.smarthomebackend.model.BlacklistedJWT;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Override
    public LoginResponse login(final String email, final String password, final HttpServletResponse response) throws InvalidCredsException {
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException ignored) {
            throw new InvalidCredsException("Given creds are not valid!");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrinciple userPrinciple = (UserPrinciple) authenticate.getPrincipal();
        UserResponse userResponse = userPrinciple.getUser();

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

    @Override
    public void logout(HttpServletRequest request) {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        try {
            String email = JWTUtils.extractEmailFromJWT(jwt);
            User usr = userService.getVerifiedUser(email);
            userService.updateUsersJWTBlacklist(usr, new BlacklistedJWT(JWTUtils.extractTokenFromJWT(jwt), usr));
        }  catch (InvalidJWTException | EntityNotFoundException ignored) {}
    }


}
