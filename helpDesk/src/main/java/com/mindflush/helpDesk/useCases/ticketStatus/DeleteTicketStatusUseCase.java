package com.mindflush.helpDesk.useCases.ticketStatus;

import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketRepository;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteTicketStatusUseCase {

    private final TicketStatusRepository ticketStatusRepository;
    private final TicketRepository ticketRepository;

    public DeleteTicketStatusUseCase(
            TicketStatusRepository ticketStatusRepository,
            TicketRepository ticketRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void execute(UUID statusId, User authenticatedUser) {

        // 1. Encontrar o status existente
        TicketStatus statusToDelete = ticketStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket status not found with ID: " + statusId));

        // 2. Regra de Negócio: O administrador só pode desativar status de sua própria empresa
        if (!statusToDelete.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to delete this ticket status.");
        }

        // 3. Regra de Negócio: Não pode deletar se houver tickets vinculados a ele
        if (!ticketRepository.findAllByStatus(statusToDelete).isEmpty()) {
            throw new IllegalStateException("Cannot delete a status that has existing tickets.");
        }

        // 4. Desativar o status (soft delete)
        statusToDelete.setActive(false);
        ticketStatusRepository.save(statusToDelete);
    }
}