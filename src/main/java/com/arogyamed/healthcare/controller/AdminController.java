package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.AdminDashboardDTO;
import com.arogyamed.healthcare.dto.AdminRequestDTO;
import com.arogyamed.healthcare.dto.AdminResponseDTO;
import com.arogyamed.healthcare.service.AdminService;
import org.springframework.web.bind.annotation.*;

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
    public AdminResponseDTO updateAdmin(
            @PathVariable Long id,
            @RequestBody AdminRequestDTO requestDTO) {

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

}
