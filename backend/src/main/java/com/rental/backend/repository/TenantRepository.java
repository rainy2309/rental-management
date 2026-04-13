package com.rental.backend.repository;

import com.rental.backend.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    boolean existsByIdNumber(String idNumber);
    boolean existsByEmail(String email);
}