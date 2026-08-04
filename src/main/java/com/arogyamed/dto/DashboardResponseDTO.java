package com.arogyamed.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {

    // ===========================
    // USER ANALYTICS
    // ===========================

    private long totalUsers;
    private long totalPatients;
    private long totalDoctors;
    private long totalPharmacists;
    private long totalCompanies;
    private long totalWholesalers;
    private long totalDeliveryPartners;
    private long totalAdmins;

    // ===========================
    // MEDICINE ANALYTICS
    // ===========================

    private long totalMedicines;
    private long totalInventory;
    private long availableMedicines;
    private long lowStockMedicines;
    private long outOfStockMedicines;

    // ===========================
    // ORDER ANALYTICS
    // ===========================

    private long totalOrders;
    private long pendingOrders;
    private long confirmedOrders;
    private long deliveredOrders;
    private long cancelledOrders;

    // ===========================
    // PAYMENT ANALYTICS
    // ===========================

    private long totalPayments;
    private double totalRevenue;
    private long successfulPayments;
    private long pendingPayments;
    private long failedPayments;

    // ===========================
    // DELIVERY ANALYTICS
    // ===========================

    private long totalDeliveries;
    private long inTransitDeliveries;
    private long completedDeliveries;

    // ===========================
    // HEALTHCARE ANALYTICS
    // ===========================

    private long totalAppointments;
    private long completedAppointments;
    private long cancelledAppointments;

    private long totalPrescriptions;
    private long totalMedicalRecords;

    // ===========================
    // EMERGENCY ANALYTICS
    // ===========================

    private long totalSOSRequests;
    private long totalAmbulances;

    // ===========================
    // REVIEW ANALYTICS
    // ===========================

    private long totalReviews;
    private double averageRating;

    // ===========================
    // QUALITY CHECK ANALYTICS
    // ===========================

    private long totalQualityChecks;
    private long approvedQualityChecks;
    private long rejectedQualityChecks;
    private long pendingQualityChecks;

    // ===========================
    // ENTERPRISE KPIs
    // ===========================

    private double medicineApprovalRate;
    private double deliverySuccessRate;
    private double appointmentCompletionRate;

    // ===========================
    // FUTURE ANALYTICS
    // ===========================

    private double monthlyRevenue;
    private long monthlyOrders;
    private long monthlyRegistrations;

}
