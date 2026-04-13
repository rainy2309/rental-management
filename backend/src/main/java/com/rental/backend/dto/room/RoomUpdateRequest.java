package com.rental.backend.dto.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomUpdateRequest {

    @NotNull(message = "Floor is required")
    private Integer floor;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Base price is required")
    @Min(value = 1, message = "Base price must be greater than 0")
    private Double basePrice;

    @NotNull(message = "Status is required")
    private String status;

    private String note;
}