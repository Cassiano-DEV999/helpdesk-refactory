package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.followUp.request.FollowUpRequestDTO;
import com.mindflush.helpDesk.dto.followUp.response.FollowUpResponseDTO;
import com.mindflush.helpDesk.entities.models.FollowUp;
import com.mindflush.helpDesk.entities.models.Ticket;
import com.mindflush.helpDesk.entities.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FollowUpMapper {

    FollowUpMapper INSTANCE = Mappers.getMapper(FollowUpMapper.class);

    // Mapeamento para a criação de um novo FollowUp
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "ticket", source = "ticket")
    FollowUp toFollowUp(FollowUpRequestDTO dto, User user, Ticket ticket);

    // Mapeamento para a resposta da API
    @Mapping(target = "user", source = "user")
    @Mapping(target = "ticket", source = "ticket")
    FollowUpResponseDTO toFollowUpResponseDTO(FollowUp followUp);

    // Mapeamento para lista de FollowUps
    List<FollowUpResponseDTO> toFollowUpResponseDTOList(List<FollowUp> followUps);

    // Mapeamento para atualizar um FollowUp existente
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFollowUpFromDto(FollowUpRequestDTO dto, @MappingTarget FollowUp followUp);
}