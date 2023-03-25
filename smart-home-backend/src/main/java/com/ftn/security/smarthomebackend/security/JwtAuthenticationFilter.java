package com.ftn.security.smarthomebackend.security;

import com.auth0.jwt.JWT;
import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.service.implementation.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
        final String authHeader = request.getHeader(HEADER_STRING);

        if (headerIsInvalid(authHeader)) {
            filterChain.doFilter(request, response);
        }
        else {
          Authentication authentication = getAuthentication(request);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          filterChain.doFilter(request, response);
        }
    }

    private boolean headerIsInvalid(String header){

        return header == null || !header.startsWith(TOKEN_PREFIX);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        Authentication authentication = null;
        try {
            authentication = getUsernamePasswordAuthentication(request);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return authentication;
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request)
            throws EntityNotFoundException {
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(TOKEN_PREFIX,"");

        String email = JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();

        return emailIsNotNull(email) ? getSpringAuthToken(email) : null;
    }

    private UsernamePasswordAuthenticationToken getSpringAuthToken(String email)
            throws EntityNotFoundException
    {
        UserResponse userResponse = new UserResponse(userService.getVerifiedUser(email));
        return getUsernamePasswordAuthenticationToken(userResponse);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserResponse userResponse) {
        UserPrinciple principal = new UserPrinciple(userResponse);

        return new UsernamePasswordAuthenticationToken(
                userResponse.getEmail(),
                principal.getPassword(),
                principal.getAuthorities()
        );
    }

    private boolean emailIsNotNull(String email){
        return email != null;
    }
}
