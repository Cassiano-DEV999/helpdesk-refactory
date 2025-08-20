package com.mindflush.helpDesk.useCases.ticket;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.TicketRepository;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignTicketUseCase {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public AssignTicketUseCase(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Ticket execute(UUID ticketId, List<UUID> assignedUserIds, User authenticatedUser) {

        // 1. Encontrar o ticket e os usuários (técnicos)
        Ticket ticketToAssign = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        List<User> usersToAssign = userRepository.findAllById(assignedUserIds);

        if (usersToAssign.size() != assignedUserIds.size()) {
            throw new IllegalArgumentException("One or more users not found.");
        }

        Company company = authenticatedUser.getCompany();
        boolean allUsersInSameCompany = usersToAssign.stream()
                .allMatch(user -> user.getCompany().equals(company));

        if (!allUsersInSameCompany) {
            throw new IllegalArgumentException("All assigned users must belong to the same company.");
        }

        if (!ticketToAssign.getDepartment().getCompany().equals(company)) {
            throw new IllegalArgumentException("You do not have permission to assign this ticket.");
        }


        ticketToAssign.setAssignedUsers(usersToAssign);

        // 4. Lógica de Status: Se o ticket estiver aberto, muda para 'ASSIGNED'
        // Você precisaria da lógica para buscar o status "ASSIGNED" da entidade TicketStatus
        // TicketStatus assignedStatus = ticketStatusRepository.findByName("ASSIGNED").get();
        // ticketToAssign.setStatus(assignedStatus);

        // 5. Salvar o ticket
        return ticketRepository.save(ticketToAssign);
    }
}