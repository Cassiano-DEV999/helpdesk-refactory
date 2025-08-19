package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.company.request.CompanyRequestDTO;
import com.mindflush.helpDesk.dto.company.response.CompanyResponseDTO;
import com.mindflush.helpDesk.entities.models.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    // O Spring se encarrega de injetar a implementação
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    // Converte de DTO para a Entidade (para criação)
    Company toCompany(CompanyRequestDTO dto);

    // Converte da Entidade para o DTO de Resposta (para retorno)
    CompanyResponseDTO toCompanyResponseDTO(Company company);

    // Atualiza a entidade com base no DTO
    void updateCompanyFromDto(CompanyRequestDTO dto, @MappingTarget Company company);
}