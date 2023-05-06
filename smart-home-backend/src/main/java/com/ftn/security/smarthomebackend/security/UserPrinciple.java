package com.ftn.security.smarthomebackend.security;

import com.ftn.security.smarthomebackend.dto.response.UserResponse;
import com.ftn.security.smarthomebackend.model.Privilege;
import com.ftn.security.smarthomebackend.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrinciple implements UserDetails {
    private final UserResponse userResponse;
    public UserPrinciple(UserResponse userResponse) {
        this.userResponse = userResponse;
    }


    private List<String> getPrivileges(Role role) {

        List<String> privileges = new ArrayList<>();
        privileges.add(role.getRoleName());
        List<Privilege> collection = new ArrayList<>(role.getPrivileges());

        for (Privilege item : collection) {
            privileges.add(item.getPrivilegeName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivileges(userResponse.getRole()));
    }

    @Override
    public String getPassword() {
        return userResponse.getPassword();
    }

    @Override
    public String getUsername() {
        return userResponse.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserResponse getUser() {
        return userResponse;
    }
}
