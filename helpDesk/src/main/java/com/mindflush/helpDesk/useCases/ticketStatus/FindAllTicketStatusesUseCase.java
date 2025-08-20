package com.mindflush.helpDesk.useCases.ticketStatus;

import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllTicketStatusesUseCase {

    private final TicketStatusRepository ticketStatusRepository;

    public FindAllTicketStatusesUseCase(TicketStatusRepository ticketStatusRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional(readOnly = true)
    public List<TicketStatus> execute(User authenticatedUser) {

        return ticketStatusRepository.findByCompany(authenticatedUser.getCompany());
    }
}