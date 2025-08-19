package com.mindflush.helpDesk.dto.company.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDTO {

    private UUID id;
    private String name;
    private String cnpj;
    private String description;
    private String address;
    private String phone;
    private String email;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}