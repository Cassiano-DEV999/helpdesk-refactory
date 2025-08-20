package com.mindflush.helpDesk.dto.ticketStatus.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatusRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;


}