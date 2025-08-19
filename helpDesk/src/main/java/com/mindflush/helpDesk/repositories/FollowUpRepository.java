package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.FollowUp;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowUpRepository extends JpaRepository<FollowUp, UUID> {

    List<FollowUp> findAllByTicket(Ticket ticket);
    List<FollowUp> findAllByUser(User user);
    List<FollowUp> findAllByTicketAndUser(Ticket ticket, User user);

}
