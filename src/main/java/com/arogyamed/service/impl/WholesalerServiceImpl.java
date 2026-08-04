package com.arogyamed.service.impl;

import com.arogyamed.dto.WholesalerRequestDTO;
import com.arogyamed.dto.WholesalerResponseDTO;
import com.arogyamed.model.User;
import com.arogyamed.model.Wholesaler;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.repository.WholesalerRepository;
import com.arogyamed.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WholesalerServiceImpl implements WholesalerService {

    @Autowired
    private WholesalerRepository wholesalerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public WholesalerResponseDTO createWholesaler(WholesalerRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                        new RuntimeException("User not found"));

        Wholesaler wholesaler = new Wholesaler();

        wholesaler.setUser(user);
        wholesaler.setCompanyName(request.getCompanyName());
        wholesaler.setLicenseNumber(request.getLicenseNumber());
        wholesaler.setGstNumber(request.getGstNumber());
        wholesaler.setWarehouseAddress(request.getWarehouseAddress());
        wholesaler.setContactPerson(request.getContactPerson());

        return mapToDTO(wholesalerRepository.save(wholesaler));
    }

    @Override
    public WholesalerResponseDTO getWholesalerByUserId(Long userId) {

        Wholesaler wholesaler = wholesalerRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Wholesaler not found"));

        return mapToDTO(wholesaler);
    }

    @Override
    public WholesalerResponseDTO updateWholesaler(Long userId, WholesalerRequestDTO request) {

        Wholesaler wholesaler = wholesalerRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Wholesaler not found"));

        wholesaler.setCompanyName(request.getCompanyName());
        wholesaler.setLicenseNumber(request.getLicenseNumber());
        wholesaler.setGstNumber(request.getGstNumber());
        wholesaler.setWarehouseAddress(request.getWarehouseAddress());
        wholesaler.setContactPerson(request.getContactPerson());

        return mapToDTO(wholesalerRepository.save(wholesaler));
    }

    private WholesalerResponseDTO mapToDTO(Wholesaler wholesaler) {

        WholesalerResponseDTO dto = new WholesalerResponseDTO();

        dto.setId(wholesaler.getId());
        dto.setUserId(wholesaler.getUser().getId());
        dto.setFullName(wholesaler.getUser().getFullName());
        dto.setEmail(wholesaler.getUser().getEmail());

        dto.setCompanyName(wholesaler.getCompanyName());
        dto.setLicenseNumber(wholesaler.getLicenseNumber());
        dto.setGstNumber(wholesaler.getGstNumber());
        dto.setWarehouseAddress(wholesaler.getWarehouseAddress());
        dto.setContactPerson(wholesaler.getContactPerson());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<WholesalerResponseDTO> searchByCompanyName(String companyName) {

        return mapToDTOList(wholesalerRepository.findByCompanyNameContainingIgnoreCase(companyName));
    }

    @Override
    public List<WholesalerResponseDTO> searchByLicenseNumber(String licenseNumber) {

        return mapToDTOList(wholesalerRepository.findByLicenseNumberContainingIgnoreCase(licenseNumber));
    }

    @Override
    public List<WholesalerResponseDTO> searchByGstNumber(String gstNumber) {

        return mapToDTOList(wholesalerRepository.findByGstNumberContainingIgnoreCase(gstNumber));
    }

    @Override
    public List<WholesalerResponseDTO> searchByWarehouseAddress(String warehouseAddress) {

        return mapToDTOList(wholesalerRepository.findByWarehouseAddressContainingIgnoreCase(warehouseAddress));
    }

    @Override
    public List<WholesalerResponseDTO> searchByContactPerson(String contactPerson) {

        return mapToDTOList(wholesalerRepository.findByContactPersonContainingIgnoreCase(contactPerson));
    }

    @Override
    public List<WholesalerResponseDTO> searchByEmail(String email) {

        return mapToDTOList(wholesalerRepository.findByUser_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<WholesalerResponseDTO> searchByPhoneNumber(String phoneNumber) {

        return mapToDTOList(wholesalerRepository.findByUser_PhoneNumberContaining(phoneNumber));
    }

    private List<WholesalerResponseDTO> mapToDTOList(List<Wholesaler> wholesalers) {

        return wholesalers.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
