package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.model.Role;
import com.ftn.security.smarthomebackend.repository.RoleRepository;
import com.ftn.security.smarthomebackend.service.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findRoleByRoleName(name);
    }
}
