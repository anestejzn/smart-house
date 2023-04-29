package com.ftn.security.smarthomebackend.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.FingerprintCookieNotFoundException;
import com.ftn.security.smarthomebackend.exception.InvalidJWTException;
import com.ftn.security.smarthomebackend.model.BlacklistedJWT;
import com.ftn.security.smarthomebackend.model.User;
import com.ftn.security.smarthomebackend.service.implementation.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.ftn.security.smarthomebackend.security.JwtProperties.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    public JwtAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (!headerIsInvalid(request.getHeader(HEADER_STRING))) {
            Authentication authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean headerIsInvalid(String header) {
        return header == null || !header.startsWith(TOKEN_PREFIX) || header.equals(TOKEN_PREFIX);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        try {
            return getUsernamePasswordAuthentication(request);
        } catch (EntityNotFoundException | InvalidJWTException | FingerprintCookieNotFoundException ignored) {
            return null;
        }
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) throws EntityNotFoundException, FingerprintCookieNotFoundException, InvalidJWTException {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        User user = userService.getVerifiedUser(JWTUtils.extractEmailFromJWT(jwt));

        if (isJwtBlacklisted(user, jwt.getToken()))
            return null;

        String rawFingerprint = getFingerprintFromCookie(request);
        if (!FingerprintUtils.verifyFingerprint(JWTUtils.extractFingerprintFromJWT(jwt), rawFingerprint)) {
            userService.updateUsersJWTBlacklist(user, new BlacklistedJWT(JWTUtils.extractTokenFromJWT(jwt), user));
            return null;
        }

        return getSpringAuthToken(user);
    }

    private UsernamePasswordAuthenticationToken getSpringAuthToken(User user) {
        return getUsernamePasswordAuthenticationToken(new UserResponse(user));
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserResponse userResponse) {
        UserPrinciple principal = new UserPrinciple(userResponse);

        return new UsernamePasswordAuthenticationToken(
                userResponse.getEmail(),
                principal.getPassword(),
                principal.getAuthorities()
        );
    }

    private String getFingerprintFromCookie(HttpServletRequest request) throws FingerprintCookieNotFoundException {
        Cookie fingerprintCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> FingerprintProperties.FINGERPRINT_COOKIE.equals(cookie.getName()))
                .findFirst().orElse(null);

        if (fingerprintCookie == null)
            throw new FingerprintCookieNotFoundException("Cookie does not contain fingerprint!");

        return fingerprintCookie.getValue();
    }

    private boolean isJwtBlacklisted(User user, String jwt) {
        for (BlacklistedJWT blJWT : user.getBlacklistedJWTs())
            if (blJWT.getJwt().equals(jwt))
                return true;
        return false;
    }

}
