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
public class PauseTicketUseCase {

    private final TicketRepository ticketRepository;
    private final TicketStatusRepository ticketStatusRepository;

    public PauseTicketUseCase(TicketRepository ticketRepository, TicketStatusRepository ticketStatusRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional
    public Ticket execute(UUID ticketId, User authenticatedUser) {

        Ticket ticketToPause = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        if (!ticketToPause.getAssignedUsers().contains(authenticatedUser)) {
            throw new IllegalArgumentException("You are not assigned to this ticket.");
        }

        TicketStatus inProgressStatus = ticketStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(() -> new IllegalStateException("Status 'IN_PROGRESS' not found."));

        if (!ticketToPause.getStatus().equals(inProgressStatus)) {
            throw new IllegalStateException("Ticket must be in 'IN_PROGRESS' status to be paused.");
        }

        TicketStatus pendingStatus = ticketStatusRepository.findByName("PENDING_CLIENT")
                .orElseThrow(() -> new IllegalStateException("Status 'PENDING_CLIENT' not found."));

        ticketToPause.setStatus(pendingStatus);
        ticketToPause.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticketToPause);
    }
}