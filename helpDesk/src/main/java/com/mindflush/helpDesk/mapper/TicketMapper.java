package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.ticket.request.TicketRequestDTO;
import com.mindflush.helpDesk.dto.ticket.response.TicketResponseDTO;
import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    // Mapeamento para a criação de um novo ‘ticket’

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    @Mapping(target = "requester", source = "requester")
    @Mapping(target = "department", source = "department")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    Ticket toTicket(TicketRequestDTO dto, User requester, Department department, List<User> assignedUsers);

    // Mapeamento para a resposta da API

    @Mapping(target = "requester", source = "requester")
    @Mapping(target = "department", source = "department")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    TicketResponseDTO toTicketResponseDTO(Ticket ticket);

    // Mapeamento para atualizar um ‘ticket’ existente

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requester", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateTicketFromDto(TicketRequestDTO dto, @MappingTarget Ticket ticket);
}