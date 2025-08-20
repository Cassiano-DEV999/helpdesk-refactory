package com.mindflush.helpDesk.useCases.user;

import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID userId, User authenticatedUser) {

        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (!userToDelete.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to delete this user.");
        }

        if (userToDelete.getId().equals(authenticatedUser.getId())) {
            throw new IllegalArgumentException("You cannot delete your own user account.");
        }

        userToDelete.setActive(false);
        userRepository.save(userToDelete);
    }
}