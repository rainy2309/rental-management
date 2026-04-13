package com.rental.backend.dto.contract;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContractResponse {
    private Long id;

    private Long roomId;
    private String roomCode;

    private Long tenantId;
    private String tenantName;

    private LocalDate startDate;
    private LocalDate endDate;

    private Double depositAmount;
    private Double monthlyRent;

    private String status;
    private String note;
}