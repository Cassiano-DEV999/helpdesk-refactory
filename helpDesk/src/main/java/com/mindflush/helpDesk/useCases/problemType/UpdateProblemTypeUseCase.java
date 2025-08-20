package com.mindflush.helpDesk.useCases.problemType;

import com.mindflush.helpDesk.dto.problemType.request.ProblemTypeRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.ProblemType;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.ProblemTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateProblemTypeUseCase {

    private final ProblemTypeRepository problemTypeRepository;

    public UpdateProblemTypeUseCase(ProblemTypeRepository problemTypeRepository) {
        this.problemTypeRepository = problemTypeRepository;
    }

    @Transactional
    public ProblemType execute(UUID problemTypeId, ProblemTypeRequestDTO dto, User authenticatedUser) {

        ProblemType problemTypeToUpdate = problemTypeRepository.findById(problemTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Problem type not found with ID: " + problemTypeId));

        Company company = authenticatedUser.getCompany();
        if (!problemTypeToUpdate.getCompany().equals(company)) {
            throw new IllegalArgumentException("You do not have permission to update this problem type.");
        }

        if (problemTypeRepository.findByNameAndCompany(dto.getName(), company).isPresent() &&
                !problemTypeToUpdate.getName().equalsIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Problem type with this name already exists for your company.");
        }

        problemTypeToUpdate.setName(dto.getName());

        return problemTypeRepository.save(problemTypeToUpdate);
    }
}