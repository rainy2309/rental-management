package com.rental.backend.service;

import com.rental.backend.dto.room.RoomCreateRequest;
import com.rental.backend.dto.room.RoomResponse;
import com.rental.backend.dto.room.RoomUpdateRequest;

import java.util.List;

public interface RoomService {
    List<RoomResponse> getAllRooms();
    RoomResponse getRoomById(Long id);
    RoomResponse createRoom(RoomCreateRequest request);
    RoomResponse updateRoom(Long id, RoomUpdateRequest request);
    void deleteRoom(Long id);
}