package com.mindflush.helpDesk.useCases.followUp;

import com.mindflush.helpDesk.entities.models.FollowUp;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.FollowUpRepository;
import com.mindflush.helpDesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FindAllFollowUpsForTicketUseCase {

    private final FollowUpRepository followUpRepository;
    private final TicketRepository ticketRepository;

    public FindAllFollowUpsForTicketUseCase(FollowUpRepository followUpRepository, TicketRepository ticketRepository) {
        this.followUpRepository = followUpRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public List<FollowUp> execute(UUID ticketId, User authenticatedUser) {

        // 1. Encontrar o ticket
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        // 2. Regra de Negócio: O usuário autenticado deve pertencer à mesma empresa do ticket
        if (!ticket.getDepartment().getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view follow-ups for this ticket.");
        }

        // 3. Buscar todos os follow-ups ordenados por data de criação
        return followUpRepository.findAllByTicketOrderByCreatedAtAsc(ticket);
    }
}