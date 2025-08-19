package com.mindflush.helpDesk.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String employeeId;
    private String name;
    private String email;
    private String phone;
    private boolean isActive;
    private LocalDateTime createdAt;

    private UUID companyId;
    private String companyName;
    private UUID roleId;
    private String roleName;
}