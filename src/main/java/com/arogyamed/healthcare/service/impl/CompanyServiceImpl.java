package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.CompanyRequestDTO;
import com.arogyamed.healthcare.dto.CompanyResponseDTO;
import com.arogyamed.healthcare.model.Company;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.CompanyRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));

        Company company = new Company();

        company.setUser(user);
        company.setCompanyName(request.getCompanyName());
        company.setLicenseNumber(request.getLicenseNumber());
        company.setGstNumber(request.getGstNumber());
        company.setCompanyAddress(request.getCompanyAddress());
        company.setContactPerson(request.getContactPerson());

        return mapToDTO(companyRepository.save(company));
    }

    @Override
    public CompanyResponseDTO getCompanyByUserId(Long userId) {

        Company company = companyRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException("Company not found"));

        return mapToDTO(company);
    }

    @Override
    public CompanyResponseDTO updateCompany(Long userId, CompanyRequestDTO request) {

        Company company = companyRepository.findByUserId(userId).orElseThrow(() ->
                new RuntimeException("Company not found"));

        company.setCompanyName(request.getCompanyName());
        company.setLicenseNumber(request.getLicenseNumber());
        company.setGstNumber(request.getGstNumber());
        company.setCompanyAddress(request.getCompanyAddress());
        company.setContactPerson(request.getContactPerson());

        return mapToDTO(companyRepository.save(company));
    }

    private CompanyResponseDTO mapToDTO(Company company) {

        CompanyResponseDTO dto = new CompanyResponseDTO();

        dto.setId(company.getId());
        dto.setUserId(company.getUser().getId());
        dto.setFullName(company.getUser().getFullName());
        dto.setEmail(company.getUser().getEmail());

        dto.setCompanyName(company.getCompanyName());
        dto.setLicenseNumber(company.getLicenseNumber());
        dto.setGstNumber(company.getGstNumber());
        dto.setCompanyAddress(company.getCompanyAddress());
        dto.setContactPerson(company.getContactPerson());

        return dto;
    }
}
