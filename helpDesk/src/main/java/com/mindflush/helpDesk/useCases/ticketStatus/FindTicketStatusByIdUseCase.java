package com.mindflush.helpDesk.useCases.ticketStatus;

import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindTicketStatusByIdUseCase {

    private final TicketStatusRepository ticketStatusRepository;

    public FindTicketStatusByIdUseCase(TicketStatusRepository ticketStatusRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional(readOnly = true)
    public TicketStatus execute(UUID statusId, User authenticatedUser) {

        // 1. Encontrar o status pelo ID
        TicketStatus statusFound = ticketStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket status not found with ID: " + statusId));

        // 2. Regra de Negócio: O usuário autenticado só pode ver status de sua própria empresa
        if (!statusFound.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view this ticket status.");
        }

        return statusFound;
    }
}