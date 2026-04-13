package com.rental.backend.controller;

import com.rental.backend.dto.tenant.TenantCreateRequest;
import com.rental.backend.dto.tenant.TenantResponse;
import com.rental.backend.dto.tenant.TenantUpdateRequest;
import com.rental.backend.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    public List<TenantResponse> getAllTenants() {
        return tenantService.getAllTenants();
    }

    @GetMapping("/{id}")
    public TenantResponse getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id);
    }

    @PostMapping
    public TenantResponse createTenant(@Valid @RequestBody TenantCreateRequest request) {
        return tenantService.createTenant(request);
    }

    @PutMapping("/{id}")
    public TenantResponse updateTenant(@PathVariable Long id,
                                       @Valid @RequestBody TenantUpdateRequest request) {
        return tenantService.updateTenant(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return "Tenant deleted successfully";
    }
}