package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.CompanyRequestDTO;
import com.arogyamed.healthcare.dto.CompanyResponseDTO;

public interface CompanyService {

    CompanyResponseDTO createCompany(CompanyRequestDTO request);

    CompanyResponseDTO getCompanyByUserId(Long userId);

    CompanyResponseDTO updateCompany(Long userId, CompanyRequestDTO request);
}
