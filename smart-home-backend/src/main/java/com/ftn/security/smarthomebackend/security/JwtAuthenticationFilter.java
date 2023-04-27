package com.ftn.security.smarthomebackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.exception.FingerprintCookieNotFoundException;
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
        } catch (EntityNotFoundException | FingerprintCookieNotFoundException ignored) {
            return null;
        }
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) throws EntityNotFoundException, FingerprintCookieNotFoundException {
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(TOKEN_PREFIX,"");
        DecodedJWT jwt = JWT.require(HMAC512(SECRET.getBytes())).build().verify(token);
        String email = jwt.getSubject();

        String rawFingerprint = getFingerprintFromCookie(request);
        if (FingerprintUtils.verifyFingerprint(jwt.getClaim(FingerprintProperties.FINGERPRINT_CLAIM).asString(), rawFingerprint))
            return email != null ? getSpringAuthToken(email) : null;

        return null;
    }

    private UsernamePasswordAuthenticationToken getSpringAuthToken(String email) throws EntityNotFoundException {
        return getUsernamePasswordAuthenticationToken(new UserResponse(userService.getVerifiedUser(email)));
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
}
