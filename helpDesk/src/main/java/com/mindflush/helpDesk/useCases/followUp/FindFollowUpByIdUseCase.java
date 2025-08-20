package com.mindflush.helpDesk.useCases.followUp;

import com.mindflush.helpDesk.entities.models.FollowUp;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.FollowUpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindFollowUpByIdUseCase {

    private final FollowUpRepository followUpRepository;

    public FindFollowUpByIdUseCase(FollowUpRepository followUpRepository) {
        this.followUpRepository = followUpRepository;
    }

    @Transactional(readOnly = true)
    public FollowUp execute(UUID followUpId, User authenticatedUser) {

        FollowUp followUpFound = followUpRepository.findById(followUpId)
                .orElseThrow(() -> new IllegalArgumentException("Follow-up not found with ID: " + followUpId));

        if (!followUpFound.getTicket().getDepartment().getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view this follow-up.");
        }

        return followUpFound;
    }
}