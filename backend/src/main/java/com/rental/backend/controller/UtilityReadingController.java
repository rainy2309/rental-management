package com.rental.backend.controller;

import com.rental.backend.dto.utility.UtilityReadingCreateRequest;
import com.rental.backend.dto.utility.UtilityReadingResponse;
import com.rental.backend.service.UtilityReadingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-readings")
@RequiredArgsConstructor
public class UtilityReadingController {

    private final UtilityReadingService utilityReadingService;

    @GetMapping
    public List<UtilityReadingResponse> getAllReadings() {
        return utilityReadingService.getAllReadings();
    }

    @GetMapping("/{id}")
    public UtilityReadingResponse getReadingById(@PathVariable Long id) {
        return utilityReadingService.getReadingById(id);
    }

    @PostMapping
    public UtilityReadingResponse createReading(@Valid @RequestBody UtilityReadingCreateRequest request) {
        return utilityReadingService.createReading(request);
    }
}