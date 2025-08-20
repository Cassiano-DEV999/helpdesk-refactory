package com.mindflush.helpDesk.useCases.problemType;

import com.mindflush.helpDesk.entities.models.ProblemType;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.ProblemTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindProblemTypeByIdUseCase {

    private final ProblemTypeRepository problemTypeRepository;

    public FindProblemTypeByIdUseCase(ProblemTypeRepository problemTypeRepository) {
        this.problemTypeRepository = problemTypeRepository;
    }

    @Transactional(readOnly = true)
    public ProblemType execute(UUID problemTypeId, User authenticatedUser) {

        ProblemType problemTypeFound = problemTypeRepository.findById(problemTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Problem type not found with ID: " + problemTypeId));

        if (!problemTypeFound.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view this problem type.");
        }

        return problemTypeFound;
    }
}