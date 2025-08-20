package com.mindflush.helpDesk.dto.ticket.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Department ID cannot be null")
    private UUID departmentId;

    private UUID assignedToId;

    @NotNull(message = "Problem Type ID cannot be null")
    private UUID problemTypeId;

    // Adicionamos o ID do status, caso o frontend queira definir um status inicial
    @NotNull(message = "Status ID cannot be null")
    private UUID statusId;
}