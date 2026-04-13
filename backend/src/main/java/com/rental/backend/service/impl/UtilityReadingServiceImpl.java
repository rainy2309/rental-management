package com.rental.backend.service.impl;

import com.rental.backend.dto.utility.UtilityReadingCreateRequest;
import com.rental.backend.dto.utility.UtilityReadingResponse;
import com.rental.backend.entity.Room;
import com.rental.backend.entity.UtilityReading;
import com.rental.backend.exception.ResourceNotFoundException;
import com.rental.backend.repository.RoomRepository;
import com.rental.backend.repository.UtilityReadingRepository;
import com.rental.backend.service.UtilityReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilityReadingServiceImpl implements UtilityReadingService {

    private final UtilityReadingRepository utilityReadingRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<UtilityReadingResponse> getAllReadings() {
        return utilityReadingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UtilityReadingResponse getReadingById(Long id) {
        UtilityReading reading = utilityReadingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utility reading not found with id: " + id));

        return mapToResponse(reading);
    }

    @Override
    public UtilityReadingResponse createReading(UtilityReadingCreateRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));

        if (utilityReadingRepository.existsByRoomIdAndReadingMonth(request.getRoomId(), request.getReadingMonth())) {
            throw new RuntimeException("Utility reading already exists for this room and month");
        }

        if (request.getElectricNew() < request.getElectricOld()) {
            throw new RuntimeException("Electric new must be greater than or equal to electric old");
        }

        if (request.getWaterNew() < request.getWaterOld()) {
            throw new RuntimeException("Water new must be greater than or equal to water old");
        }

        UtilityReading reading = UtilityReading.builder()
                .room(room)
                .readingMonth(request.getReadingMonth().trim())
                .electricOld(request.getElectricOld())
                .electricNew(request.getElectricNew())
                .waterOld(request.getWaterOld())
                .waterNew(request.getWaterNew())
                .electricUnitPrice(request.getElectricUnitPrice())
                .waterUnitPrice(request.getWaterUnitPrice())
                .build();

        return mapToResponse(utilityReadingRepository.save(reading));
    }

    private UtilityReadingResponse mapToResponse(UtilityReading reading) {
        int electricUsage = reading.getElectricNew() - reading.getElectricOld();
        int waterUsage = reading.getWaterNew() - reading.getWaterOld();

        double electricFee = electricUsage * reading.getElectricUnitPrice();
        double waterFee = waterUsage * reading.getWaterUnitPrice();

        return UtilityReadingResponse.builder()
                .id(reading.getId())
                .roomId(reading.getRoom().getId())
                .roomCode(reading.getRoom().getRoomCode())
                .readingMonth(reading.getReadingMonth())
                .electricOld(reading.getElectricOld())
                .electricNew(reading.getElectricNew())
                .electricUsage(electricUsage)
                .electricUnitPrice(reading.getElectricUnitPrice())
                .electricFee(electricFee)
                .waterOld(reading.getWaterOld())
                .waterNew(reading.getWaterNew())
                .waterUsage(waterUsage)
                .waterUnitPrice(reading.getWaterUnitPrice())
                .waterFee(waterFee)
                .build();
    }
}