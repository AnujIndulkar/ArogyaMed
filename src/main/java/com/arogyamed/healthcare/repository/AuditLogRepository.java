package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // =========================
    // USER SEARCH
    // =========================

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByUserNameContainingIgnoreCase(String userName);

    // =========================
    // ROLE SEARCH
    // =========================

    List<AuditLog> findByRole(Role role);

    List<AuditLog> findByRoleAndActionStatus(Role role, ActionStatus actionStatus);

    List<AuditLog> findByRoleAndActionType(Role role, ActionType actionType);

    // =========================
    // MODULE SEARCH
    // =========================

    List<AuditLog> findByModuleName(String moduleName);

    List<AuditLog> findByModuleNameContainingIgnoreCase(String moduleName);

    List<AuditLog> findByModuleNameAndActionStatus(
            String moduleName,
            ActionStatus actionStatus
    );

    // =========================
    // ACTION SEARCH
    // =========================

    List<AuditLog> findByActionType(ActionType actionType);

    List<AuditLog> findByActionStatus(ActionStatus actionStatus);

    // =========================
    // ENTITY SEARCH
    // =========================

    List<AuditLog> findByEntityNameContainingIgnoreCase(String entityName);

    List<AuditLog> findByEntityId(Long entityId);

    // =========================
    // NETWORK SEARCH
    // =========================

    List<AuditLog> findByIpAddress(String ipAddress);

    List<AuditLog> findByHttpMethod(String httpMethod);

    List<AuditLog> findByRequestUrlContainingIgnoreCase(String requestUrl);

    // =========================
    // DEVICE SEARCH
    // =========================

    List<AuditLog> findByBrowserContainingIgnoreCase(String browser);

    List<AuditLog> findByOperatingSystemContainingIgnoreCase(String operatingSystem);

    // =========================
    // RESPONSE SEARCH
    // =========================

    List<AuditLog> findByResponseStatus(Integer responseStatus);

    List<AuditLog> findBySuccess(Boolean success);

    // =========================
    // DATE SEARCH
    // =========================

    List<AuditLog> findByActionTimeBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    // =========================
    // ADVANCED SEARCH
    // =========================

    List<AuditLog> findByUserNameContainingIgnoreCaseAndModuleNameContainingIgnoreCase(
            String userName,
            String moduleName
    );

    List<AuditLog> findByActionTypeAndActionStatus(
            ActionType actionType,
            ActionStatus actionStatus
    );

    List<AuditLog> findByModuleNameAndActionType(
            String moduleName,
            ActionType actionType
    );

    // =========================
    // DASHBOARD
    // =========================

    long countByActionStatus(ActionStatus actionStatus);

    long countByActionType(ActionType actionType);

    long countByModuleName(String moduleName);

    long countBySuccess(Boolean success);

    // =========================
    // RECENT LOGS
    // =========================

    List<AuditLog> findTop20ByOrderByActionTimeDesc();

}