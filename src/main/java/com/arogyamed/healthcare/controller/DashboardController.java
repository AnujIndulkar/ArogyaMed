package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.DashboardResponseDTO;
import com.arogyamed.healthcare.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // ==========================
    // Admin Dashboard
    // ==========================

    @GetMapping("/admin")
    public DashboardResponseDTO getAdminDashboard() {

        return dashboardService.getAdminDashboard();
    }

    // ==========================
    // Doctor Dashboard
    // ==========================

    @GetMapping("/doctor/{doctorId}")
    public DashboardResponseDTO getDoctorDashboard(@PathVariable Long doctorId) {

        return dashboardService.getDoctorDashboard(doctorId);
    }

    // ==========================
    // Patient Dashboard
    // ==========================

    @GetMapping("/patient/{patientId}")
    public DashboardResponseDTO getPatientDashboard(@PathVariable Long patientId) {

        return dashboardService.getPatientDashboard(patientId);
    }

    // ==========================
    // Company Dashboard
    // ==========================

    @GetMapping("/company/{companyId}")
    public DashboardResponseDTO getCompanyDashboard(@PathVariable Long companyId) {

        return dashboardService.getCompanyDashboard(companyId);
    }

    // ==========================
    // Pharmacist Dashboard
    // ==========================

    @GetMapping("/pharmacist/{pharmacistId}")
    public DashboardResponseDTO getPharmacistDashboard(@PathVariable Long pharmacistId) {

        return dashboardService.getPharmacistDashboard(pharmacistId);
    }

    // ==========================
    // Wholesaler Dashboard
    // ==========================

    @GetMapping("/wholesaler/{wholesalerId}")
    public DashboardResponseDTO getWholesalerDashboard(@PathVariable Long wholesalerId) {

        return dashboardService.getWholesalerDashboard(wholesalerId);
    }

    // ==========================
    // Delivery Partner Dashboard
    // ==========================

    @GetMapping("/delivery-partner/{deliveryPartnerId}")
    public DashboardResponseDTO getDeliveryPartnerDashboard(@PathVariable Long deliveryPartnerId) {

        return dashboardService.getDeliveryPartnerDashboard(deliveryPartnerId);
    }

    // ==========================
    // Ambulance Dashboard
    // ==========================

    @GetMapping("/ambulance/{ambulanceId}")
    public DashboardResponseDTO getAmbulanceDashboard(@PathVariable Long ambulanceId) {

        return dashboardService.getAmbulanceDashboard(ambulanceId);
    }

}