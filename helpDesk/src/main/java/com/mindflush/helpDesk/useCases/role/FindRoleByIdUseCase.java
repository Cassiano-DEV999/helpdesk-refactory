package com.mindflush.helpDesk.useCases.role;

import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindRoleByIdUseCase {

    private final RoleRepository roleRepository;

    public FindRoleByIdUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Role execute(UUID roleId, User authenticatedUser) {

        Role roleFound = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));

        if (!roleFound.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view this role.");
        }

        return roleFound;
    }
}