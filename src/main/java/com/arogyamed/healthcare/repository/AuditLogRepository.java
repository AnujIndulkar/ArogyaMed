package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.ActionStatus;
import com.arogyamed.healthcare.model.ActionType;
import com.arogyamed.healthcare.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // =========================
    // User Based Search
    // =========================

    List<AuditLog> findByUserId(Long userId);

    // =========================
    // Role Based Search
    // =========================

    List<AuditLog> findByRole(String role);

    // =========================
    // Module Based Search
    // =========================

    List<AuditLog> findByModuleName(String moduleName);

    // =========================
    // Action Type Search
    // =========================

    List<AuditLog> findByActionType(ActionType actionType);

    // =========================
    // Status Search
    // =========================

    List<AuditLog> findByActionStatus(ActionStatus actionStatus);

    // =========================
    // Date Range Search
    // =========================

    List<AuditLog> findByActionTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    // =========================
    // Dashboard Counts
    // =========================

    long countByActionStatus(ActionStatus actionStatus);

    long countByActionType(ActionType actionType);

    long countByModuleName(String moduleName);

    // =========================
    // Recent Activities
    // =========================

    List<AuditLog> findTop20ByOrderByActionTimeDesc();

}