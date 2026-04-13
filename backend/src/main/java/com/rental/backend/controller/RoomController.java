package com.rental.backend.controller;

import com.rental.backend.dto.room.RoomCreateRequest;
import com.rental.backend.dto.room.RoomResponse;
import com.rental.backend.dto.room.RoomUpdateRequest;
import com.rental.backend.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public RoomResponse createRoom(@Valid @RequestBody RoomCreateRequest request) {
        return roomService.createRoom(request);
    }

    @PutMapping("/{id}")
    public RoomResponse updateRoom(@PathVariable Long id,
                                   @Valid @RequestBody RoomUpdateRequest request) {
        return roomService.updateRoom(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "Room deleted successfully";
    }
}