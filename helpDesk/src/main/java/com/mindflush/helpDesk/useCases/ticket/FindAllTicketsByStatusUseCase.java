package com.mindflush.helpDesk.useCases.ticket;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketRepository;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindAllTicketsByStatusUseCase {

    private final TicketRepository ticketRepository;
    private final TicketStatusRepository ticketStatusRepository;

    public FindAllTicketsByStatusUseCase(TicketRepository ticketRepository, TicketStatusRepository ticketStatusRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional(readOnly = true)
    public Page<Ticket> execute(UUID statusId, User authenticatedUser, Pageable pageable) {
        // 1. Encontrar o status no banco de dados
        TicketStatus status = ticketStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status not found with ID: " + statusId));

        // 2. Obter a empresa do usuário autenticado para o filtro de segurança
        Company company = authenticatedUser.getCompany();

        // 3. Buscar os tickets pelo status e pela empresa
        return ticketRepository.findAllByStatusAndDepartmentCompany(status, company, pageable);
    }
}