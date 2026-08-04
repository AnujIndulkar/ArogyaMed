package com.arogyamed.service;

import com.arogyamed.dto.DashboardResponseDTO;

public interface DashboardService {

    DashboardResponseDTO getAdminDashboard();

    DashboardResponseDTO getDoctorDashboard(Long doctorId);

    DashboardResponseDTO getPatientDashboard(Long patientId);

    DashboardResponseDTO getCompanyDashboard(Long companyId);

    DashboardResponseDTO getWholesalerDashboard(Long wholesalerId);

    DashboardResponseDTO getPharmacistDashboard(Long pharmacistId);

    DashboardResponseDTO getDeliveryPartnerDashboard(Long deliveryPartnerId);

    DashboardResponseDTO getAmbulanceDashboard(Long ambulanceId);

}
