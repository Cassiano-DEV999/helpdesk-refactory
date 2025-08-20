package com.mindflush.helpDesk.useCases.followUp;

import com.mindflush.helpDesk.dto.followUp.request.FollowUpRequestDTO;
import com.mindflush.helpDesk.entities.models.FollowUp;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.FollowUpRepository;
import com.mindflush.helpDesk.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddFollowUpToTicketUseCase {

    private final FollowUpRepository followUpRepository;
    private final TicketRepository ticketRepository;

    public AddFollowUpToTicketUseCase(FollowUpRepository followUpRepository, TicketRepository ticketRepository) {
        this.followUpRepository = followUpRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public FollowUp execute(FollowUpRequestDTO dto, User authenticatedUser) {

        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + dto.getTicketId()));

        if (!ticket.getDepartment().getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to add a follow-up to this ticket.");
        }

        FollowUp newFollowUp = new FollowUp();
        newFollowUp.setContent(dto.getContent());
        newFollowUp.setTicket(ticket);
        newFollowUp.setUser(authenticatedUser);
        newFollowUp.setCreatedAt(LocalDateTime.now());

        // 4. Salvar o novo FollowUp
        return followUpRepository.save(newFollowUp);
    }
}