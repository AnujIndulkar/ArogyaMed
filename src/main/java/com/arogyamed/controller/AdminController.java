package com.arogyamed.controller;

import com.arogyamed.dto.AdminDashboardDTO;
import com.arogyamed.dto.AdminRequestDTO;
import com.arogyamed.dto.AdminResponseDTO;
import com.arogyamed.service.AdminService;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.model.AdminDepartment;
import com.arogyamed.model.AdminStatus;
import com.arogyamed.model.AdminType;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Create Admin
    @PostMapping
    public AdminResponseDTO createAdmin(@RequestBody AdminRequestDTO requestDTO) {
        return adminService.createAdmin(requestDTO);
    }

    // Get Admin By ID
    @GetMapping("/{id}")
    public AdminResponseDTO getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    // Get All Admins
    @GetMapping
    public List<AdminResponseDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    // Update Admin
    @PutMapping("/{id}")
    public AdminResponseDTO updateAdmin(@PathVariable Long id, @RequestBody AdminRequestDTO requestDTO) {

        return adminService.updateAdmin(id, requestDTO);
    }

    // Delete Admin
    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Long id) {

        adminService.deleteAdmin(id);

        return "Admin deleted successfully.";
    }

    // Block Admin
    @PutMapping("/block/{id}")
    public AdminResponseDTO blockAdmin(@PathVariable Long id) {

        return adminService.blockAdmin(id);
    }

    // Unblock Admin
    @PutMapping("/unblock/{id}")
    public AdminResponseDTO unblockAdmin(@PathVariable Long id) {

        return adminService.unblockAdmin(id);
    }

    // Dashboard
    @GetMapping("/dashboard")
    public AdminDashboardDTO getDashboard() {

        return adminService.getDashboard();
    }

    // ==========================
    // Advanced Search & Filtering
    // ==========================

    // Search by Employee ID
    @GetMapping("/search/employee-id")
    public AdminResponseDTO searchByEmployeeId(@RequestParam String employeeId) {

        return adminService.searchByEmployeeId(employeeId);
    }

    // Search by Department
    @GetMapping("/search/department")
    public List<AdminResponseDTO> searchByDepartment(
            @RequestParam AdminDepartment department) {

        return adminService.searchByDepartment(department);
    }

    // Search by Status
    @GetMapping("/search/status")
    public List<AdminResponseDTO> searchByStatus(@RequestParam AdminStatus status) {

        return adminService.searchByStatus(status);
    }

    // Search by Admin Type
    @GetMapping("/search/type")
    public List<AdminResponseDTO> searchByAdminType(@RequestParam AdminType adminType) {

        return adminService.searchByAdminType(adminType);
    }

    // Search by Designation
    @GetMapping("/search/designation")
    public List<AdminResponseDTO> searchByDesignation(@RequestParam String designation) {

        return adminService.searchByDesignation(designation);
    }

    // Search by Office Location
    @GetMapping("/search/location")
    public List<AdminResponseDTO> searchByOfficeLocation(@RequestParam String officeLocation) {

        return adminService.searchByOfficeLocation(officeLocation);
    }

    // Search by Joining Date
    @GetMapping("/search/joining-date")
    public List<AdminResponseDTO> searchByJoiningDate(@RequestParam LocalDate joiningDate) {

        return adminService.searchByJoiningDate(joiningDate);
    }

    // Search by Department and Status
    @GetMapping("/search/department-status")
    public List<AdminResponseDTO> searchByDepartmentAndStatus(@RequestParam AdminDepartment department, @RequestParam AdminStatus status) {

        return adminService.searchByDepartmentAndStatus(department, status);
    }

    // Search by Admin Type and Status
    @GetMapping("/search/type-status")
    public List<AdminResponseDTO> searchByAdminTypeAndStatus(@RequestParam AdminType adminType, @RequestParam AdminStatus status) {

        return adminService.searchByAdminTypeAndStatus(adminType, status);
    }
}
