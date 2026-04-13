package com.rental.backend.service;

import com.rental.backend.dto.utility.UtilityReadingCreateRequest;
import com.rental.backend.dto.utility.UtilityReadingResponse;

import java.util.List;

public interface UtilityReadingService {
    List<UtilityReadingResponse> getAllReadings();
    UtilityReadingResponse getReadingById(Long id);
    UtilityReadingResponse createReading(UtilityReadingCreateRequest request);
}