package com.ftn.security.smarthomebackend.config;

import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.exception.EntityNotFoundException;
import com.ftn.security.smarthomebackend.security.UserPrinciple;
import com.ftn.security.smarthomebackend.service.implementation.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private final UserService userService;

        public CustomUserDetailsService(UserService userService) {
            this.userService = userService;
        }

        @Override
        public UserDetails loadUserByUsername(final String email) {

            UserResponse userDTO;
            try {
                userDTO = new UserResponse(userService.getVerifiedUser(email));
            } catch (EntityNotFoundException e) {

                return null;
            }

            return new UserPrinciple(userDTO);

        }


}
