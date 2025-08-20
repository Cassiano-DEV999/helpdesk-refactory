package com.mindflush.helpDesk.useCases.user;

import com.mindflush.helpDesk.dto.user.request.UserRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.RoleRepository;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User execute(UserRequestDTO userData, User authenticatedUser) {
        if (userRepository.findByEmail(userData.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já em uso.");
        }
        if (userRepository.findByEmployeeId(userData.getEmployeeId()).isPresent()) {
            throw new IllegalArgumentException("Employee ID already in use.");
        }
        Company company = authenticatedUser.getCompany();

        Role role = roleRepository.findById(userData.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado"));

        if (!role.getCompany().equals(company)) {
            throw new IllegalArgumentException("Cargo não pertence a está companhia.");
        }

        User newUser = new User();
        newUser.setName(userData.getName());
        newUser.setEmail(userData.getEmail());
        newUser.setEmployeeId(userData.getEmployeeId());

        newUser.setPassword(passwordEncoder.encode(userData.getPassword()));

        newUser.setCompany(company);
        newUser.setRole(role);
        newUser.setActive(true);

        return userRepository.save(newUser);
    }
}