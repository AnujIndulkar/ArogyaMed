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

import java.util.List;
import java.util.stream.Collectors;

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

    // ================= Search =================

    @Override
    public List<CompanyResponseDTO> searchByCompanyName(String companyName) {

        return mapToDTOList(companyRepository.findByCompanyNameContainingIgnoreCase(companyName));
    }

    @Override
    public List<CompanyResponseDTO> searchByLicenseNumber(String licenseNumber) {

        return mapToDTOList(companyRepository.findByLicenseNumberContainingIgnoreCase(licenseNumber));
    }

    @Override
    public List<CompanyResponseDTO> searchByGstNumber(String gstNumber) {

        return mapToDTOList(companyRepository.findByGstNumberContainingIgnoreCase(gstNumber));
    }

    @Override
    public List<CompanyResponseDTO> searchByContactPerson(String contactPerson) {

        return mapToDTOList(companyRepository.findByContactPersonContainingIgnoreCase(contactPerson));
    }

    @Override
    public List<CompanyResponseDTO> searchByCompanyAddress(String companyAddress) {

        return mapToDTOList(companyRepository.findByCompanyAddressContainingIgnoreCase(companyAddress));
    }

    @Override
    public List<CompanyResponseDTO> searchByEmail(String email) {

        return mapToDTOList(companyRepository.findByUser_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<CompanyResponseDTO> searchByPhoneNumber(String phoneNumber) {

        return mapToDTOList(companyRepository.findByUser_PhoneNumberContaining(phoneNumber));
    }

    private List<CompanyResponseDTO> mapToDTOList(List<Company> companies) {

        return companies.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
