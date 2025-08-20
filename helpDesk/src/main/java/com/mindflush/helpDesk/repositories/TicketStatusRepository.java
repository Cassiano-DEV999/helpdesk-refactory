package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, UUID> {
    Optional<TicketStatus> findByName(String name);

    Optional<Object> findByNameAndCompany(@NotBlank(message = "Name cannot be blank") String name, Company company);

    List<TicketStatus> findByCompany(Company company);
}

