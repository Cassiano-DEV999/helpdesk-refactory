package com.mindflush.helpDesk.useCases.role;

import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllRolesByCompanyUseCase {

    private final RoleRepository roleRepository;

    public FindAllRolesByCompanyUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> execute(User authenticatedUser) {

        return roleRepository.findByCompany(authenticatedUser.getCompany());
    }
}