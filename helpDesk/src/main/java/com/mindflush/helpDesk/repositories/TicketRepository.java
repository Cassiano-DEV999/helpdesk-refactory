package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findAllByStatus(TicketStatus status);
    Page<Ticket> findAllByStatus(TicketStatus status, Pageable pageable);
    Page<Ticket> findAllByRequester(User requester, Pageable pageable);
    Page<Ticket> findAllByAssignedUsers(List<User> assignedUsers, Pageable pageable);
    List<Ticket> findAllByDepartment(Department department);
    Page<Ticket> findAllByRequesterAndStatus(User requester, TicketStatus status, Pageable pageable);
    Page<Ticket> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String description, Pageable pageable);
    Page<Ticket> findAllByStatusAndDepartmentCompany(TicketStatus status,
                                                     Company company, Pageable pageable);
    List<Ticket> findAllByProblemType(ProblemType problemTypeToDelete);
}