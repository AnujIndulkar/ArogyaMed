package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.AuditDashboardDTO;
import com.arogyamed.healthcare.dto.AuditLogRequestDTO;
import com.arogyamed.healthcare.dto.AuditLogResponseDTO;
import com.arogyamed.healthcare.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {

    // =========================
    // CRUD
    // =========================

    AuditLogResponseDTO createAuditLog(AuditLogRequestDTO requestDTO);

    AuditLogResponseDTO getAuditLogById(Long id);

    List<AuditLogResponseDTO> getAllAuditLogs();

    void deleteAuditLog(Long id);

    // =========================
    // USER SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByUserId(Long userId);

    List<AuditLogResponseDTO> getAuditLogsByUserName(String userName);

    // =========================
    // ROLE SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByRole(Role role);

    List<AuditLogResponseDTO> getAuditLogsByRoleAndStatus(
            Role role,
            ActionStatus actionStatus
    );

    List<AuditLogResponseDTO> getAuditLogsByRoleAndActionType(
            Role role,
            ActionType actionType
    );

    // =========================
    // MODULE SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByModuleName(String moduleName);

    List<AuditLogResponseDTO> getAuditLogsByModuleAndStatus(
            String moduleName,
            ActionStatus actionStatus
    );

    // =========================
    // ACTION SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByActionType(ActionType actionType);

    List<AuditLogResponseDTO> getAuditLogsByActionStatus(ActionStatus actionStatus);

    // =========================
    // ENTITY SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByEntityName(String entityName);

    List<AuditLogResponseDTO> getAuditLogsByEntityId(Long entityId);

    // =========================
    // NETWORK SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByIpAddress(String ipAddress);

    List<AuditLogResponseDTO> getAuditLogsByHttpMethod(String httpMethod);

    List<AuditLogResponseDTO> getAuditLogsByRequestUrl(String requestUrl);

    // =========================
    // DEVICE SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByBrowser(String browser);

    List<AuditLogResponseDTO> getAuditLogsByOperatingSystem(String operatingSystem);

    // =========================
    // RESPONSE SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByResponseStatus(Integer responseStatus);

    List<AuditLogResponseDTO> getAuditLogsBySuccess(Boolean success);

    // =========================
    // DATE SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsBetweenDates(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    // =========================
    // ADVANCED SEARCH
    // =========================

    List<AuditLogResponseDTO> getAuditLogsByUserNameAndModule(
            String userName,
            String moduleName
    );

    // =========================
    // RECENT LOGS
    // =========================

    List<AuditLogResponseDTO> getRecentAuditLogs();

    // =========================
    // DASHBOARD
    // =========================

    AuditDashboardDTO getAuditDashboard();

}