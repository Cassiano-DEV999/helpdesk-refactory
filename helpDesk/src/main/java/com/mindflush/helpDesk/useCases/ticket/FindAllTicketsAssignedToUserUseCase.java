package com.mindflush.helpDesk.useCases.ticket;

import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllTicketsAssignedToUserUseCase {

    private final TicketRepository ticketRepository;

    public FindAllTicketsAssignedToUserUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public Page<Ticket> execute(User authenticatedUser, Pageable pageable) {
        // Usa o usuário autenticado como o técnico atribuído
        return ticketRepository.findAllByAssignedUsers((List<User>) authenticatedUser, pageable);
    }
}