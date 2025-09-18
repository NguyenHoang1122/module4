package com.demospringboot.services;



import com.demospringboot.dto.RoleDTO;
import com.demospringboot.models.Role;
import com.demospringboot.repositories.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    public RoleDTO getById(Long id) {
        return roleRepository.findById(id)
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .orElse(null);
    }

    public void createRole(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        roleRepository.save(role);
    }

    public void updateRole(Long id, RoleDTO dto) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            role.setName(dto.getName());
            roleRepository.save(role);
        }
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
