package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.CompanyRequestDTO;
import com.arogyamed.healthcare.dto.CompanyResponseDTO;
import com.arogyamed.healthcare.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public CompanyResponseDTO createCompany(@RequestBody CompanyRequestDTO request) {
        return companyService.createCompany(request);
    }

    @GetMapping("/{userId}")
    public CompanyResponseDTO getCompanyByUserId(@PathVariable Long userId) {
        return companyService.getCompanyByUserId(userId);
    }

    @PutMapping("/{userId}")
    public CompanyResponseDTO updateCompany(@PathVariable Long userId, @RequestBody CompanyRequestDTO request) {
        return companyService.updateCompany(userId, request);
    }
}
