package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AdminDashboardDTO;
import com.arogyamed.healthcare.dto.AdminRequestDTO;
import com.arogyamed.healthcare.dto.AdminResponseDTO;

import java.util.List;

public interface AdminService {

    AdminResponseDTO createAdmin(AdminRequestDTO requestDTO);

    AdminResponseDTO getAdminById(Long id);

    List<AdminResponseDTO> getAllAdmins();

    AdminResponseDTO updateAdmin(Long id, AdminRequestDTO requestDTO);

    void deleteAdmin(Long id);

    AdminDashboardDTO getDashboard();

    AdminResponseDTO blockAdmin(Long id);

    AdminResponseDTO unblockAdmin(Long id);

}

