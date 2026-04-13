package com.rental.backend.controller;

import com.rental.backend.dto.contract.ContractCreateRequest;
import com.rental.backend.dto.contract.ContractResponse;
import com.rental.backend.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public List<ContractResponse> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/{id}")
    public ContractResponse getContractById(@PathVariable Long id) {
        return contractService.getContractById(id);
    }

    @PostMapping
    public ContractResponse createContract(@Valid @RequestBody ContractCreateRequest request) {
        return contractService.createContract(request);
    }

    @PostMapping("/{id}/terminate")
    public ContractResponse terminateContract(@PathVariable Long id) {
        return contractService.terminateContract(id);
    }
}