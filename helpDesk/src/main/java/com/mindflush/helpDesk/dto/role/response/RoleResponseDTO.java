package com.mindflush.helpDesk.dto.role.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {

    private UUID id;
    private String name;


    private UUID companyId;
    private String companyName;
}