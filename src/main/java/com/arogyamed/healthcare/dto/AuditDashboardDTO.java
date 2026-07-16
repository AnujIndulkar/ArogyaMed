package com.arogyamed.healthcare.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditDashboardDTO {

    // =========================
    // Total Logs
    // =========================

    private long totalLogs;

    // =========================
    // Status Analytics
    // =========================

    private long successfulActions;

    private long failedActions;

    private long warningActions;

    // =========================
    // Module Analytics
    // =========================

    private long loginCount;

    private long orderCount;

    private long paymentCount;

    private long medicineCount;

    private long inventoryCount;

    private long appointmentCount;

}
