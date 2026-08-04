package com.arogyamed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboardDTO {

    private Long totalUsers;

    private Long totalPatients;

    private Long totalDoctors;

    private Long totalPharmacists;

    private Long totalCompanies;

    private Long totalWholesalers;

    private Long totalDeliveryPartners;

    private Long totalAmbulances;

    private Long totalMedicines;

    private Long totalOrders;

    private Long totalPayments;

    private Double totalRevenue;

    private Long totalReviews;

    private Long totalSOSRequests;

    private Long pendingKyc;

    private Long approvedKyc;

    private Long rejectedKyc;

}
