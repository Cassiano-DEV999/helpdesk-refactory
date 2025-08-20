package com.mindflush.helpDesk.useCases.role;

import com.mindflush.helpDesk.dto.role.request.RoleRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.mapper.RoleMapper;
import com.mindflush.helpDesk.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public CreateRoleUseCase(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Transactional
    public Role execute(RoleRequestDTO dto, User authenticatedUser) {

        Company company = authenticatedUser.getCompany();

        if (roleRepository.findByNameAndCompany(dto.getName(), company).isPresent()) {
            throw new IllegalArgumentException("Role with this name already exists in this company.");
        }

        Role newRole = new Role();
        newRole.setName(dto.getName());
        newRole.setCompany(company);

        return roleRepository.save(newRole);
    }
}