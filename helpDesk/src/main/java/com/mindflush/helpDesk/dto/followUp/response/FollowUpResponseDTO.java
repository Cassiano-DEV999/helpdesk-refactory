package com.mindflush.helpDesk.dto.followUp.response;

import com.mindflush.helpDesk.dto.ticket.response.TicketResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowUpResponseDTO {

    private UUID id;
    private String content;
    private LocalDateTime createdAt;

    // Relacionamentos: usamos DTOs aninhados para expor apenas o essencial
    private SimpleTicketDTO ticket;
    private SimpleUserDTO user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleTicketDTO {
        private UUID id;
        private String title;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleUserDTO {
        private UUID id;
        private String name;
    }
}