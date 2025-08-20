package com.mindflush.helpDesk.useCases.ticket;

import com.mindflush.helpDesk.dto.ticket.request.TicketRequestDTO;
import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.ProblemType;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.DepartmentRepository;
import com.mindflush.helpDesk.repositories.ProblemTypeRepository;
import com.mindflush.helpDesk.repositories.TicketRepository;
import com.mindflush.helpDesk.repositories.TicketStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class CreateTicketUseCase {

    private final TicketRepository ticketRepository;
    private final DepartmentRepository departmentRepository;
    private final ProblemTypeRepository problemTypeRepository;
    private final TicketStatusRepository ticketStatusRepository;

    public CreateTicketUseCase(
            TicketRepository ticketRepository,
            DepartmentRepository departmentRepository,
            ProblemTypeRepository problemTypeRepository,
            TicketStatusRepository ticketStatusRepository) {
        this.ticketRepository = ticketRepository;
        this.departmentRepository = departmentRepository;
        this.problemTypeRepository = problemTypeRepository;
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Transactional
    public Ticket execute(TicketRequestDTO dto, User authenticatedUser) {
        // 1. Obter as entidades relacionadas
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found."));

        ProblemType problemType = problemTypeRepository.findById(dto.getProblemTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Problem type not found."));

        // Regra de negócio: o departamento deve pertencer à empresa do usuário
        if (!department.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("Department does not belong to your company.");
        }

        // Definir o status inicial como 'OPEN'
        TicketStatus openStatus = ticketStatusRepository.findByName("OPEN")
                .orElseThrow(() -> new IllegalStateException("Initial status 'OPEN' not found."));

        // 2. Criar a nova entidade Ticket
        Ticket newTicket = new Ticket();
        newTicket.setTitle(dto.getTitle());
        newTicket.setDescription(dto.getDescription());
        newTicket.setDepartment(department);
        newTicket.setProblemType(problemType);
        newTicket.setRequester(authenticatedUser);
        newTicket.setStatus(openStatus);
        newTicket.setCreatedAt(LocalDateTime.now());

        return ticketRepository.save(newTicket);
    }
}