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
public class ResolveTicketUseCase {

    private final TicketRepository ticketRepository;
    private final TicketStatusRepository ticketStatusRepository;

    public ResolveTicketUseCase(TicketRepository ticketRepository, TicketStatusRepository ticketStatusRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional
    public Ticket execute(UUID ticketId, User authenticatedUser) {

        // 1. Encontrar o ticket
        Ticket ticketToResolve = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        // 2. Regra de Negócio: O usuário autenticado deve estar entre os técnicos atribuídos
        if (!ticketToResolve.getAssignedUsers().contains(authenticatedUser)) {
            throw new IllegalArgumentException("You are not assigned to this ticket.");
        }

        // 3. Regra de Negócio: O ticket precisa estar em um status de trabalho para ser resolvido
        TicketStatus inProgressStatus = ticketStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(() -> new IllegalStateException("Status 'IN_PROGRESS' not found."));

        TicketStatus pendingStatus = ticketStatusRepository.findByName("PENDING_CLIENT")
                .orElseThrow(() -> new IllegalStateException("Status 'PENDING_CLIENT' not found."));

        if (!ticketToResolve.getStatus().equals(inProgressStatus) && !ticketToResolve.getStatus().equals(pendingStatus)) {
            throw new IllegalStateException("Ticket must be in 'IN_PROGRESS' or 'PENDING_CLIENT' status to be resolved.");
        }

        // 4. Encontrar o status 'RESOLVED'
        TicketStatus resolvedStatus = ticketStatusRepository.findByName("RESOLVED")
                .orElseThrow(() -> new IllegalStateException("Status 'RESOLVED' not found."));

        // 5. Atualizar o status e os timestamps
        ticketToResolve.setStatus(resolvedStatus);
        ticketToResolve.setUpdatedAt(LocalDateTime.now());
        ticketToResolve.setResolvedAt(LocalDateTime.now());

        // 6. Salvar o ticket
        return ticketRepository.save(ticketToResolve);
    }
}