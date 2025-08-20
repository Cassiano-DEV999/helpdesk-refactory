package com.mindflush.helpDesk.useCases.problemType;

import com.mindflush.helpDesk.dto.problemType.request.ProblemTypeRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.ProblemType;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.ProblemTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProblemTypeUseCase {

    private final ProblemTypeRepository problemTypeRepository;

    public CreateProblemTypeUseCase(ProblemTypeRepository problemTypeRepository) {
        this.problemTypeRepository = problemTypeRepository;
    }

    @Transactional
    public ProblemType execute(ProblemTypeRequestDTO dto, User authenticatedUser) {

        Company company = authenticatedUser.getCompany();

        if (problemTypeRepository.findByNameAndCompany(dto.getName(), company).isPresent()) {
            throw new IllegalArgumentException("Problem type with this name already exists in this company.");
        }

        ProblemType newProblemType = new ProblemType();
        newProblemType.setName(dto.getName());
        newProblemType.setCompany(company);

        return problemTypeRepository.save(newProblemType);
    }
}