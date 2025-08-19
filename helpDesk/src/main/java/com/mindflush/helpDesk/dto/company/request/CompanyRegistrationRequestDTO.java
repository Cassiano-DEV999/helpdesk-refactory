package com.mindflush.helpDesk.dto.company.request;

import com.mindflush.helpDesk.dto.company.request.CompanyRequestDTO;
import com.mindflush.helpDesk.dto.user.request.UserRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRegistrationRequestDTO {

    @Valid
    @NotNull(message = "Company data cannot be null")
    private CompanyRequestDTO companyData;

    @Valid
    @NotNull(message = "User data cannot be null")
    private UserRequestDTO userData;
}