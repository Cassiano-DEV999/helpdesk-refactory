package com.mindflush.helpDesk.useCases.role;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.RoleRepository;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;

@Service
public class DeleteRoleUseCase {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DeleteRoleUseCase(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID roleToDeleteId, UUID newRoleId, User authenticatedUser) {

        Role roleToDelete = roleRepository.findById(roleToDeleteId)
                .orElseThrow(() -> new IllegalArgumentException("Role to delete not found with ID: " + roleToDeleteId));

        Role newRole = roleRepository.findById(newRoleId)
                .orElseThrow(() -> new IllegalArgumentException("New role not found with ID: " + newRoleId));

        Company company = authenticatedUser.getCompany();
        if (!roleToDelete.getCompany().equals(company) || !newRole.getCompany().equals(company)) {
            throw new IllegalArgumentException("Roles do not belong to your company.");
        }

        if (roleToDelete.getId().equals(newRole.getId())) {
            throw new IllegalArgumentException("Cannot reassign users to the same role being deleted.");
        }

        List<User> usersWithOldRole = userRepository.findByRole(roleToDelete);
        for (User user : usersWithOldRole) {
            user.setRole(newRole);
            userRepository.save(user);
        }

        roleRepository.delete(roleToDelete);
    }
}