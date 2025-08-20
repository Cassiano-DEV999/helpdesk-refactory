package com.mindflush.helpDesk.useCases.user;

import com.mindflush.helpDesk.dto.user.request.UserRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.mapper.UserMapper;
import com.mindflush.helpDesk.repositories.RoleRepository;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UpdateUserUseCase(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public User execute(UUID userId, UserRequestDTO dto, User authenticatedUser) {

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (!userToUpdate.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to update this user.");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent() && !userToUpdate.getEmail().equals(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use by another user.");
        }
        if (userRepository.findByEmployeeId(dto.getEmployeeId()).isPresent() && !userToUpdate.getEmployeeId().equals(dto.getEmployeeId())) {
            throw new IllegalArgumentException("Employee ID is already in use by another user.");
        }

        Role newRole = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + dto.getRoleId()));

        if (!newRole.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("Role does not belong to your company.");
        }

        userToUpdate.setName(dto.getName());
        userToUpdate.setEmail(dto.getEmail());
        userToUpdate.setEmployeeId(dto.getEmployeeId());
        userToUpdate.setRole(newRole);


        return userRepository.save(userToUpdate);
    }
}