package com.mindflush.helpDesk.useCases.ticket;

import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketRepository;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StartTicketUseCase {

    private final TicketRepository ticketRepository;
    private final TicketStatusRepository ticketStatusRepository;

    public StartTicketUseCase(TicketRepository ticketRepository, TicketStatusRepository ticketStatusRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional
    public Ticket execute(UUID ticketId, User authenticatedUser) {

        Ticket ticketToStart = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        if (!ticketToStart.getAssignedUsers().contains(authenticatedUser)) {
            throw new IllegalArgumentException("You are not assigned to this ticket.");
        }

        TicketStatus assignedStatus = ticketStatusRepository.findByName("ASSIGNED")
                .orElseThrow(() -> new IllegalStateException("Status 'ASSIGNED' not found."));

        if (!ticketToStart.getStatus().equals(assignedStatus)) {
            throw new IllegalStateException("Ticket must be in 'ASSIGNED' status to be started.");
        }

        TicketStatus inProgressStatus = ticketStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(() -> new IllegalStateException("Status 'IN_PROGRESS' not found."));

        ticketToStart.setStatus(inProgressStatus);
        ticketToStart.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticketToStart);
    }
}