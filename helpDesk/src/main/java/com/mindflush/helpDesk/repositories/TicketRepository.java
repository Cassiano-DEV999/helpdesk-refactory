package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.enums.TicketStatus;
import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Page<Ticket> findAllByStatus(TicketStatus status, Pageable pageable);
    Page<Ticket> findAllByRequester(User requester, Pageable pageable);
    List<Ticket> findAllByAssignedUsers(User assignedUsers);
    List<Ticket> findAllByDepartment(Department department);
    Page<Ticket> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String description, Pageable pageable);
}
