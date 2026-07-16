package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AuditDashboardDTO;
import com.arogyamed.healthcare.dto.AuditLogRequestDTO;
import com.arogyamed.healthcare.dto.AuditLogResponseDTO;
import com.arogyamed.healthcare.model.ActionStatus;
import com.arogyamed.healthcare.model.ActionType;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {

    // =========================
    // CRUD Operations
    // =========================

    AuditLogResponseDTO createAuditLog(AuditLogRequestDTO requestDTO);

    AuditLogResponseDTO getAuditLogById(Long id);

    List<AuditLogResponseDTO> getAllAuditLogs();

    void deleteAuditLog(Long id);

    // =========================
    // Search Operations
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByUserId(Long userId);

    List<AuditLogResponseDTO> getAuditLogsByRole(String role);

    List<AuditLogResponseDTO> getAuditLogsByModuleName(String moduleName);

    List<AuditLogResponseDTO> getAuditLogsByActionType(ActionType actionType);

    List<AuditLogResponseDTO> getAuditLogsByActionStatus(ActionStatus actionStatus);

    List<AuditLogResponseDTO> getAuditLogsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    // =========================
    // Recent Activity
    // =========================

    List<AuditLogResponseDTO> getRecentAuditLogs();

    // =========================
    // Dashboard
    // =========================

    AuditDashboardDTO getAuditDashboard();

}