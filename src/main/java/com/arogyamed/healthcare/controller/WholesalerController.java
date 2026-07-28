package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.WholesalerRequestDTO;
import com.arogyamed.healthcare.dto.WholesalerResponseDTO;
import com.arogyamed.healthcare.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wholesalers")
public class WholesalerController {

    @Autowired
    private WholesalerService wholesalerService;

    @PostMapping
    public WholesalerResponseDTO createWholesaler(@RequestBody WholesalerRequestDTO request) {
        return wholesalerService.createWholesaler(request);
    }

    // ================= Search APIs =================

    @GetMapping("/{userId}")
    public WholesalerResponseDTO getWholesalerByUserId(@PathVariable Long userId) {
        return wholesalerService.getWholesalerByUserId(userId);
    }

    // Search by Company Name
    @GetMapping("/search/company-name")
    public List<WholesalerResponseDTO> searchByCompanyName(@RequestParam String companyName) {
        return wholesalerService.searchByCompanyName(companyName);
    }

    // Search by License Number
    @GetMapping("/search/license-number")
    public List<WholesalerResponseDTO> searchByLicenseNumber(@RequestParam String licenseNumber) {
        return wholesalerService.searchByLicenseNumber(licenseNumber);
    }

    // Search by GST Number
    @GetMapping("/search/gst-number")
    public List<WholesalerResponseDTO> searchByGstNumber(@RequestParam String gstNumber) {
        return wholesalerService.searchByGstNumber(gstNumber);
    }

    // Search by Warehouse Address
    @GetMapping("/search/warehouse-address")
    public List<WholesalerResponseDTO> searchByWarehouseAddress(@RequestParam String warehouseAddress) {
        return wholesalerService.searchByWarehouseAddress(warehouseAddress);
    }

    // Search by Contact Person
    @GetMapping("/search/contact-person")
    public List<WholesalerResponseDTO> searchByContactPerson(@RequestParam String contactPerson) {
        return wholesalerService.searchByContactPerson(contactPerson);
    }

    // Search by Email
    @GetMapping("/search/email")
    public List<WholesalerResponseDTO> searchByEmail(@RequestParam String email) {
        return wholesalerService.searchByEmail(email);
    }

    // Search by Phone Number
    @GetMapping("/search/phone-number")
    public List<WholesalerResponseDTO> searchByPhoneNumber(@RequestParam String phoneNumber) {
        return wholesalerService.searchByPhoneNumber(phoneNumber);
    }

    @PutMapping("/{userId}")
    public WholesalerResponseDTO updateWholesaler(@PathVariable Long userId, @RequestBody WholesalerRequestDTO request) {
        return wholesalerService.updateWholesaler(userId, request);
    }

}
