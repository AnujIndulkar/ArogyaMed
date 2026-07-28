package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AdminDashboardDTO;
import com.arogyamed.healthcare.dto.AdminRequestDTO;
import com.arogyamed.healthcare.dto.AdminResponseDTO;
import com.arogyamed.healthcare.model.AdminDepartment;
import com.arogyamed.healthcare.model.AdminStatus;
import com.arogyamed.healthcare.model.AdminType;

import java.time.LocalDate;
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

    // ===========================
    // Advanced Search & Filtering
    // ===========================

    List<AdminResponseDTO> searchByDepartment(AdminDepartment department);

    List<AdminResponseDTO> searchByStatus(AdminStatus status);

    List<AdminResponseDTO> searchByAdminType(AdminType adminType);

    List<AdminResponseDTO> searchByDesignation(String designation);

    List<AdminResponseDTO> searchByOfficeLocation(String officeLocation);

    AdminResponseDTO searchByEmployeeId(String employeeId);

    List<AdminResponseDTO> searchByJoiningDate(LocalDate joiningDate);

    List<AdminResponseDTO> searchByDepartmentAndStatus(AdminDepartment department, AdminStatus status);

    List<AdminResponseDTO> searchByAdminTypeAndStatus(AdminType adminType, AdminStatus status);

}