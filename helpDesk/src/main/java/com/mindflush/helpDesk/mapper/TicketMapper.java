package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.ticket.request.TicketRequestDTO;
import com.mindflush.helpDesk.dto.ticket.response.TicketResponseDTO;
import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.ProblemType;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import com.mindflush.helpDesk.entities.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    @Mapping(target = "requester", source = "requester")
    @Mapping(target = "department", source = "department")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    @Mapping(target = "problemType", source = "problemType")
    Ticket toTicket(TicketRequestDTO dto, User requester, Department department, List<User> assignedUsers, ProblemType problemType, TicketStatus status);

    @Mapping(target = "requester", source = "requester")
    @Mapping(target = "department", source = "department")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    @Mapping(target = "problemType.id", source = "problemType.id")
    @Mapping(target = "problemType.name", source = "problemType.name")
    @Mapping(target = "status.id", source = "status.id")
    @Mapping(target = "status.name", source = "status.name")
    TicketResponseDTO toTicketResponseDTO(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requester", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    @Mapping(target = "problemType", ignore = true)
    void updateTicketFromDto(TicketRequestDTO dto, @MappingTarget Ticket ticket);
}