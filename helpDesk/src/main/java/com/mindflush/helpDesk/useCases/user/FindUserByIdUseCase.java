package com.mindflush.helpDesk.useCases.user;

import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindUserByIdUseCase {

    private final UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User execute(UUID userId, User authenticatedUser) {

        User userFound = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (!userFound.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view this user.");
        }

        return userFound;
    }
}