package com.mindflush.helpDesk.useCases.ticketStatus;

import com.mindflush.helpDesk.dto.ticketStatus.request.TicketStatusRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTicketStatusUseCase {

    private final TicketStatusRepository ticketStatusRepository;

    public CreateTicketStatusUseCase(TicketStatusRepository ticketStatusRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional
    public TicketStatus execute(TicketStatusRequestDTO dto, User authenticatedUser) {

        // 1. Obter a empresa do usuário autenticado
        Company company = authenticatedUser.getCompany();

        // 2. Validar a unicidade do nome do status dentro do escopo da empresa
        if (ticketStatusRepository.findByNameAndCompany(dto.getName(), company).isPresent()) {
            throw new IllegalArgumentException("Ticket status with this name already exists for your company.");
        }

        // 3. Criar a nova entidade TicketStatus
        TicketStatus newStatus = new TicketStatus();
        newStatus.setName(dto.getName());
        newStatus.setCompany(company); // Vincula o status à empresa

        // 4. Salvar o novo status
        return ticketStatusRepository.save(newStatus);
    }
}