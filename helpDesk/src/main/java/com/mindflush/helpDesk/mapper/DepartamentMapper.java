package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.departament.request.DepartamentRequestDTO;
import com.mindflush.helpDesk.dto.departament.response.DepartamentResponseDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartamentMapper {

    DepartamentMapper INSTANCE = Mappers.getMapper(DepartamentMapper.class);

    // Mapeamento para a criação de um novo departamento
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", source = "company")
    Department toDepartment(DepartamentRequestDTO dto, Company company);

    // Mapeamento para a resposta da API
    @Mapping(target = "companyId", source = "company.id")
    DepartamentResponseDTO toDepartmentResponseDTO(Department department);

    // Mapeamento para atualizar um departamento existente
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateDepartmentFromDto(DepartamentRequestDTO dto, @MappingTarget Department department);

    // Mapeamento para lista de departamentos
    List<DepartamentResponseDTO> toDepartmentResponseDTOList(List<Department> departments);
}