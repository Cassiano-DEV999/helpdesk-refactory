package com.mindflush.helpDesk.dto.ticket.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {

    private UUID id;
    private String title;
    private String description;

    // Converte os enums para String para a resposta da API
    private String problemType;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;

    // Relacionamentos: DTOs aninhados para expor apenas o necessário
    private SimpleUserDTO requester;
    private SimpleDepartmentDTO department;
    private List<SimpleUserDTO> assignedUsers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleUserDTO {
        private UUID id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleDepartmentDTO {
        private UUID id;
        private String name;
    }
}