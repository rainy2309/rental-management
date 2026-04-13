package com.rental.backend.service;

import com.rental.backend.dto.tenant.TenantCreateRequest;
import com.rental.backend.dto.tenant.TenantResponse;
import com.rental.backend.dto.tenant.TenantUpdateRequest;

import java.util.List;

public interface TenantService {
    List<TenantResponse> getAllTenants();
    TenantResponse getTenantById(Long id);
    TenantResponse createTenant(TenantCreateRequest request);
    TenantResponse updateTenant(Long id, TenantUpdateRequest request);
    void deleteTenant(Long id);
}