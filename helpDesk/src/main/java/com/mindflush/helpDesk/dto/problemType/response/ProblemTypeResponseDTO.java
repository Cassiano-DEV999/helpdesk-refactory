package com.mindflush.helpDesk.dto.problemType.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTypeResponseDTO {

    private UUID id;
    private String name;

}