package com.rental.backend.dto.tenant;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TenantResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String idNumber;
    private LocalDate birthDate;
    private String address;
    private String emergencyContact;
}