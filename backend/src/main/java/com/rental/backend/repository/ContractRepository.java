package com.rental.backend.repository;

import com.rental.backend.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    boolean existsByRoomIdAndStatus(Long roomId, String status);
    List<Contract> findByStatus(String status);
}