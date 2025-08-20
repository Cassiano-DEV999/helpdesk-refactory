package com.mindflush.helpDesk.useCases.ticket;

import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindTicketByIdUseCase {

    private final TicketRepository ticketRepository;

    public FindTicketByIdUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public Ticket execute(UUID ticketId, User authenticatedUser) {

        Ticket ticketFound = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        if (!ticketFound.getDepartment().getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view this ticket.");
        }

        return ticketFound;
    }
}