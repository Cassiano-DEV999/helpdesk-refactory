package com.mindflush.helpDesk.useCases.ticketStatus;

import com.mindflush.helpDesk.dto.ticketStatus.request.TicketStatusRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateTicketStatusUseCase {

    private final TicketStatusRepository ticketStatusRepository;

    public UpdateTicketStatusUseCase(TicketStatusRepository ticketStatusRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional
    public TicketStatus execute(UUID statusId, TicketStatusRequestDTO dto, User authenticatedUser) {

        TicketStatus statusToUpdate = ticketStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket status not found with ID: " + statusId));

        Company company = authenticatedUser.getCompany();
        if (!statusToUpdate.getCompany().equals(company)) {
            throw new IllegalArgumentException("You do not have permission to update this ticket status.");
        }

        if (ticketStatusRepository.findByNameAndCompany(dto.getName(), company).isPresent() &&
                !statusToUpdate.getName().equalsIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Ticket status with this name already exists for your company.");
        }

        statusToUpdate.setName(dto.getName());

        return ticketStatusRepository.save(statusToUpdate);
    }
}