package com.mindflush.helpDesk.dto.departament.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Company ID cannot be null")
    private UUID companyId;
}