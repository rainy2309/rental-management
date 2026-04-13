package com.rental.backend.service.impl;

import com.rental.backend.dto.contract.ContractCreateRequest;
import com.rental.backend.dto.contract.ContractResponse;
import com.rental.backend.entity.Contract;
import com.rental.backend.entity.Room;
import com.rental.backend.entity.Tenant;
import com.rental.backend.exception.ResourceNotFoundException;
import com.rental.backend.repository.ContractRepository;
import com.rental.backend.repository.RoomRepository;
import com.rental.backend.repository.TenantRepository;
import com.rental.backend.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final RoomRepository roomRepository;
    private final TenantRepository tenantRepository;

    @Override
    public List<ContractResponse> getAllContracts() {
        return contractRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ContractResponse getContractById(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        return mapToResponse(contract);
    }

    @Override
    public ContractResponse createContract(ContractCreateRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));

        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + request.getTenantId()));

        String normalizedStatus = request.getStatus().trim().toUpperCase();

        if (!normalizedStatus.equals("ACTIVE")
                && !normalizedStatus.equals("EXPIRED")
                && !normalizedStatus.equals("TERMINATED")) {
            throw new RuntimeException("Invalid contract status");
        }

        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        if (normalizedStatus.equals("ACTIVE")
                && contractRepository.existsByRoomIdAndStatus(room.getId(), "ACTIVE")) {
            throw new RuntimeException("This room already has an active contract");
        }

        Contract contract = Contract.builder()
                .room(room)
                .tenant(tenant)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .depositAmount(request.getDepositAmount())
                .monthlyRent(request.getMonthlyRent())
                .status(normalizedStatus)
                .note(request.getNote())
                .build();

        Contract saved = contractRepository.save(contract);

        if (normalizedStatus.equals("ACTIVE")) {
            room.setStatus("OCCUPIED");
            roomRepository.save(room);
        }

        return mapToResponse(saved);
    }

    @Override
    public ContractResponse terminateContract(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        contract.setStatus("TERMINATED");
        Contract saved = contractRepository.save(contract);

        Room room = contract.getRoom();
        room.setStatus("AVAILABLE");
        roomRepository.save(room);

        return mapToResponse(saved);
    }

    private ContractResponse mapToResponse(Contract contract) {
        return ContractResponse.builder()
                .id(contract.getId())
                .roomId(contract.getRoom().getId())
                .roomCode(contract.getRoom().getRoomCode())
                .tenantId(contract.getTenant().getId())
                .tenantName(contract.getTenant().getFullName())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .depositAmount(contract.getDepositAmount())
                .monthlyRent(contract.getMonthlyRent())
                .status(contract.getStatus())
                .note(contract.getNote())
                .build();
    }
}