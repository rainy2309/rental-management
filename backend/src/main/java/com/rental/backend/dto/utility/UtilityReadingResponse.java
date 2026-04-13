package com.rental.backend.dto.utility;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilityReadingResponse {
    private Long id;
    private Long roomId;
    private String roomCode;
    private String readingMonth;

    private Integer electricOld;
    private Integer electricNew;
    private Integer electricUsage;
    private Double electricUnitPrice;
    private Double electricFee;

    private Integer waterOld;
    private Integer waterNew;
    private Integer waterUsage;
    private Double waterUnitPrice;
    private Double waterFee;
}