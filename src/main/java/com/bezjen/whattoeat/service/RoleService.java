package com.bezjen.whattoeat.service;

import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.entity.Role;
import com.bezjen.whattoeat.repository.RoleRepository;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean saveRole(String role) {
        Role roleFromDb = roleRepository.findByName(role);

        if (roleFromDb != null) {
            return false;
        }
        
        Role newRole = new Role(role);
        
        roleRepository.save(newRole);
        return true;
    }
}