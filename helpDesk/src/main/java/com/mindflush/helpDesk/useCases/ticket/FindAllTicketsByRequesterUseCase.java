package com.mindflush.helpDesk.useCases.ticket;

import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllTicketsByRequesterUseCase {

    private final TicketRepository ticketRepository;

    public FindAllTicketsByRequesterUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public Page<Ticket> execute(User authenticatedUser, Pageable pageable) {
        return ticketRepository.findAllByRequester(authenticatedUser, pageable);
    }
}