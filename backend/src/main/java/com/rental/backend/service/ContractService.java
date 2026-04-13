package com.rental.backend.service;

import com.rental.backend.dto.contract.ContractCreateRequest;
import com.rental.backend.dto.contract.ContractResponse;

import java.util.List;

public interface ContractService {
    List<ContractResponse> getAllContracts();
    ContractResponse getContractById(Long id);
    ContractResponse createContract(ContractCreateRequest request);
    ContractResponse terminateContract(Long id);
}