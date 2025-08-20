package com.mindflush.helpDesk.useCases.problemType;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.ProblemType;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.ProblemTypeRepository;
import com.mindflush.helpDesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteProblemTypeUseCase {

    private final ProblemTypeRepository problemTypeRepository;
    private final TicketRepository ticketRepository;

    public DeleteProblemTypeUseCase(ProblemTypeRepository problemTypeRepository, TicketRepository ticketRepository) {
        this.problemTypeRepository = problemTypeRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void execute(UUID problemTypeId, User authenticatedUser) {

        ProblemType problemTypeToDelete = problemTypeRepository.findById(problemTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Problem type not found with ID: " + problemTypeId));

        Company company = authenticatedUser.getCompany();
        if (!problemTypeToDelete.getCompany().equals(company)) {
            throw new IllegalArgumentException("You do not have permission to delete this problem type.");
        }

        if (!ticketRepository.findAllByProblemType(problemTypeToDelete).isEmpty()) {
            throw new IllegalStateException("Cannot delete a problem type that has existing tickets.");
        }

        problemTypeToDelete.setActive(false);

        problemTypeRepository.save(problemTypeToDelete);
    }
}