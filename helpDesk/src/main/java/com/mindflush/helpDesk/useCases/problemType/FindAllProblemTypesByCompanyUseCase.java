package com.mindflush.helpDesk.useCases.problemType;

import com.mindflush.helpDesk.entities.models.ProblemType;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.ProblemTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllProblemTypesByCompanyUseCase {

    private final ProblemTypeRepository problemTypeRepository;

    public FindAllProblemTypesByCompanyUseCase(ProblemTypeRepository problemTypeRepository) {
        this.problemTypeRepository = problemTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<ProblemType> execute(User authenticatedUser) {

        return problemTypeRepository.findByCompany(authenticatedUser.getCompany());
    }
}