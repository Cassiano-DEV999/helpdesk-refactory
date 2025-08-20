package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.problemType.request.ProblemTypeRequestDTO;
import com.mindflush.helpDesk.dto.problemType.response.ProblemTypeResponseDTO;
import com.mindflush.helpDesk.entities.models.ProblemType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProblemTypeMapper {

    ProblemType toEntity(ProblemTypeRequestDTO dto);
    ProblemTypeResponseDTO toResponseDTO(ProblemType entity);
    void updateEntityFromDto(ProblemTypeRequestDTO dto, @MappingTarget ProblemType entity);
}