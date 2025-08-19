package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.role.request.RoleRequestDTO;
import com.mindflush.helpDesk.dto.role.response.RoleResponseDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    // Mapeamento para a criação de um novo cargo
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", source = "company")
    Role toRole(RoleRequestDTO dto, Company company);

    // Mapeamento para a resposta da API
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.name")
    RoleResponseDTO toRoleResponseDTO(Role role);

    // Mapeamento para atualizar um cargo existente
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateRoleFromDto(RoleRequestDTO dto, @MappingTarget Role role);

    // Mapeamento para lista de cargos
    List<RoleResponseDTO> toRoleResponseDTOList(List<Role> roles);
}