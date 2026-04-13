package com.rental.backend.dto.contract;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractCreateRequest {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Tenant ID is required")
    private Long tenantId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Deposit amount is required")
    @Min(value = 0, message = "Deposit amount must be >= 0")
    private Double depositAmount;

    @NotNull(message = "Monthly rent is required")
    @Min(value = 1, message = "Monthly rent must be > 0")
    private Double monthlyRent;

    @NotNull(message = "Status is required")
    private String status;

    private String note;
}