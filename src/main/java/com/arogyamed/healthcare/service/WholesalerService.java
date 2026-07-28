package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.WholesalerRequestDTO;
import com.arogyamed.healthcare.dto.WholesalerResponseDTO;

import java.util.List;

public interface WholesalerService {

    // ================= CRUD =================

    WholesalerResponseDTO createWholesaler(WholesalerRequestDTO request);

    WholesalerResponseDTO getWholesalerByUserId(Long userId);

    WholesalerResponseDTO updateWholesaler(Long userId, WholesalerRequestDTO request);

    // ================= Search =================

    // Search by Company Name
    List<WholesalerResponseDTO> searchByCompanyName(String companyName);

    // Search by License Number
    List<WholesalerResponseDTO> searchByLicenseNumber(String licenseNumber);

    // Search by GST Number
    List<WholesalerResponseDTO> searchByGstNumber(String gstNumber);

    // Search by Warehouse Address
    List<WholesalerResponseDTO> searchByWarehouseAddress(String warehouseAddress);

    // Search by Contact Person
    List<WholesalerResponseDTO> searchByContactPerson(String contactPerson);

    // Search by Email
    List<WholesalerResponseDTO> searchByEmail(String email);

    // Search by Phone Number
    List<WholesalerResponseDTO> searchByPhoneNumber(String phoneNumber);
}
