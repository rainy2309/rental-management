package com.rental.backend.service.impl;

import com.rental.backend.dto.tenant.TenantCreateRequest;
import com.rental.backend.dto.tenant.TenantResponse;
import com.rental.backend.dto.tenant.TenantUpdateRequest;
import com.rental.backend.entity.Tenant;
import com.rental.backend.exception.ResourceNotFoundException;
import com.rental.backend.repository.TenantRepository;
import com.rental.backend.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Override
    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TenantResponse getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + id));

        return mapToResponse(tenant);
    }

    @Override
    public TenantResponse createTenant(TenantCreateRequest request) {
        if (tenantRepository.existsByIdNumber(request.getIdNumber())) {
            throw new RuntimeException("ID number already exists");
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()
                && tenantRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Tenant tenant = Tenant.builder()
                .fullName(request.getFullName().trim())
                .phone(request.getPhone().trim())
                .email(normalizeNullable(request.getEmail()))
                .idNumber(request.getIdNumber().trim())
                .birthDate(request.getBirthDate())
                .address(normalizeNullable(request.getAddress()))
                .emergencyContact(normalizeNullable(request.getEmergencyContact()))
                .build();

        return mapToResponse(tenantRepository.save(tenant));
    }

    @Override
    public TenantResponse updateTenant(Long id, TenantUpdateRequest request) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + id));

        if (!tenant.getIdNumber().equals(request.getIdNumber())
                && tenantRepository.existsByIdNumber(request.getIdNumber())) {
            throw new RuntimeException("ID number already exists");
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            boolean emailChanged = tenant.getEmail() == null || !tenant.getEmail().equals(request.getEmail());
            if (emailChanged && tenantRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
        }

        tenant.setFullName(request.getFullName().trim());
        tenant.setPhone(request.getPhone().trim());
        tenant.setEmail(normalizeNullable(request.getEmail()));
        tenant.setIdNumber(request.getIdNumber().trim());
        tenant.setBirthDate(request.getBirthDate());
        tenant.setAddress(normalizeNullable(request.getAddress()));
        tenant.setEmergencyContact(normalizeNullable(request.getEmergencyContact()));

        return mapToResponse(tenantRepository.save(tenant));
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + id));

        tenantRepository.delete(tenant);
    }

    private String normalizeNullable(String value) {
        if (value == null || value.isBlank()) return null;
        return value.trim();
    }

    private TenantResponse mapToResponse(Tenant tenant) {
        return TenantResponse.builder()
                .id(tenant.getId())
                .fullName(tenant.getFullName())
                .phone(tenant.getPhone())
                .email(tenant.getEmail())
                .idNumber(tenant.getIdNumber())
                .birthDate(tenant.getBirthDate())
                .address(tenant.getAddress())
                .emergencyContact(tenant.getEmergencyContact())
                .build();
    }
}