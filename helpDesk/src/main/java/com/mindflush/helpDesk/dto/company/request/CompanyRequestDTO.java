package com.mindflush.helpDesk.dto.company.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDTO {

    @NotBlank
    private String name;

    @NotBlank(message = "CNPJ cannot be blank")
    private String cnpj;

    private String description;
    private String address;
    private String phone;

    @Email(message = "Invalid email format")
    private String email;
}