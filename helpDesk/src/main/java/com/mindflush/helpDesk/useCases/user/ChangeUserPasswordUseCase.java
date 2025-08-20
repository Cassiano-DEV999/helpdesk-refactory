package com.mindflush.helpDesk.useCases.user;

import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeUserPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangeUserPasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void execute(String oldPassword, String newPassword, User authenticatedUser) {

        User userToUpdate = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!passwordEncoder.matches(oldPassword, userToUpdate.getPassword())) {
            throw new IllegalArgumentException("Incorrect old password.");
        }

        if (passwordEncoder.matches(newPassword, userToUpdate.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password.");
        }

        userToUpdate.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userToUpdate);
    }
}