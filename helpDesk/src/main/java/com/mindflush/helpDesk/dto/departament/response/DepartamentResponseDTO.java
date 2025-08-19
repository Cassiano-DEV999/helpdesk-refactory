package com.mindflush.helpDesk.dto.departament.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentResponseDTO {

    private UUID id;
    private String name;
    private UUID companyId;
}