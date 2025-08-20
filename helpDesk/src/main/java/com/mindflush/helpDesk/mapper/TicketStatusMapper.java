package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.ticketStatus.request.TicketStatusRequestDTO;
import com.mindflush.helpDesk.dto.ticketStatus.response.TicketStatusResponseDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.TicketStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketStatusMapper {

    TicketStatusMapper INSTANCE = Mappers.getMapper(TicketStatusMapper.class);

    /**
     * Converte um DTO de requisição para uma nova entidade TicketStatus.
     * <p>
     * Este método é usado para a criação de um novo status,
     * vinculando-o à empresa fornecida pelo UseCase.
     *
     * @param dto O DTO de entrada.
     * @param company A entidade Company a ser vinculada.
     * @return Uma nova entidade TicketStatus.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", source = "company")
    TicketStatus toEntity(TicketStatusRequestDTO dto, Company company);

    /**
     * Converte uma entidade TicketStatus para um DTO de resposta.
     * <p>
     * O mapeamento "achata" o relacionamento com a empresa,
     * expondo apenas a ID e o nome no DTO.
     *
     * @param entity A entidade TicketStatus a ser convertida.
     * @return O DTO de resposta correspondente.
     */
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.name")
    TicketStatusResponseDTO toResponseDTO(TicketStatus entity);

    /**
     * Atualiza uma entidade TicketStatus existente com base nos dados de um DTO.
     * <p>
     * Este método é usado para operações de atualização, ignorando
     * campos que não devem ser alterados, como a ID e a empresa.
     *
     * @param dto O DTO de entrada.
     * @param entity A entidade TicketStatus alvo da atualização.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateEntityFromDto(TicketStatusRequestDTO dto, @MappingTarget TicketStatus entity);

    /**
     * Converte uma lista de entidades TicketStatus para uma lista de DTOs.
     *
     * @param entities A lista de entidades.
     * @return Uma lista de DTOs de resposta.
     */
    List<TicketStatusResponseDTO> toResponseDTOList(List<TicketStatus> entities);
}