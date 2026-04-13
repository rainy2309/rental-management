package com.rental.backend.repository;

import com.rental.backend.entity.UtilityReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilityReadingRepository extends JpaRepository<UtilityReading, Long> {
    boolean existsByRoomIdAndReadingMonth(Long roomId, String readingMonth);
}