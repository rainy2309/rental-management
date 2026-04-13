package com.rental.backend.dto.utility;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UtilityReadingCreateRequest {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotBlank(message = "Reading month is required")
    private String readingMonth; // yyyy-MM

    @NotNull(message = "Electric old is required")
    @Min(value = 0, message = "Electric old must be >= 0")
    private Integer electricOld;

    @NotNull(message = "Electric new is required")
    @Min(value = 0, message = "Electric new must be >= 0")
    private Integer electricNew;

    @NotNull(message = "Water old is required")
    @Min(value = 0, message = "Water old must be >= 0")
    private Integer waterOld;

    @NotNull(message = "Water new is required")
    @Min(value = 0, message = "Water new must be >= 0")
    private Integer waterNew;

    @NotNull(message = "Electric unit price is required")
    @Min(value = 1, message = "Electric unit price must be > 0")
    private Double electricUnitPrice;

    @NotNull(message = "Water unit price is required")
    @Min(value = 1, message = "Water unit price must be > 0")
    private Double waterUnitPrice;
}