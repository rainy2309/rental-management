package com.rental.backend.service.impl;

import com.rental.backend.dto.room.RoomCreateRequest;
import com.rental.backend.dto.room.RoomResponse;
import com.rental.backend.dto.room.RoomUpdateRequest;
import com.rental.backend.entity.Room;
import com.rental.backend.exception.ResourceNotFoundException;
import com.rental.backend.repository.RoomRepository;
import com.rental.backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        return mapToResponse(room);
    }

    @Override
    public RoomResponse createRoom(RoomCreateRequest request) {
        if (roomRepository.existsByRoomCode(request.getRoomCode())) {
            throw new RuntimeException("Room code already exists");
        }

        validateStatus(request.getStatus());

        Room room = Room.builder()
                .roomCode(request.getRoomCode().trim())
                .floor(request.getFloor())
                .capacity(request.getCapacity())
                .basePrice(request.getBasePrice())
                .status(request.getStatus().trim().toUpperCase())
                .note(request.getNote())
                .build();

        return mapToResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse updateRoom(Long id, RoomUpdateRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        validateStatus(request.getStatus());

        room.setFloor(request.getFloor());
        room.setCapacity(request.getCapacity());
        room.setBasePrice(request.getBasePrice());
        room.setStatus(request.getStatus().trim().toUpperCase());
        room.setNote(request.getNote());

        return mapToResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        roomRepository.delete(room);
    }

    private void validateStatus(String status) {
        String normalized = status.trim().toUpperCase();
        if (!normalized.equals("AVAILABLE")
                && !normalized.equals("OCCUPIED")
                && !normalized.equals("MAINTENANCE")) {
            throw new RuntimeException("Invalid room status");
        }
    }

    private RoomResponse mapToResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomCode(room.getRoomCode())
                .floor(room.getFloor())
                .capacity(room.getCapacity())
                .basePrice(room.getBasePrice())
                .status(room.getStatus())
                .note(room.getNote())
                .build();
    }
}