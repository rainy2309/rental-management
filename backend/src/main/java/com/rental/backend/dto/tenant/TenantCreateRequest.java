package com.rental.backend.dto.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TenantCreateRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone is required")
    private String phone;

    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "ID number is required")
    private String idNumber;

    private LocalDate birthDate;

    private String address;

    private String emergencyContact;
}