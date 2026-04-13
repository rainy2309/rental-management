package com.rental.backend.dto.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponse {
    private Long id;
    private String roomCode;
    private Integer floor;
    private Integer capacity;
    private Double basePrice;
    private String status;
    private String note;
}