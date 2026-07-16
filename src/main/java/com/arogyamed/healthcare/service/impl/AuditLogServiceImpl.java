package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.AuditDashboardDTO;
import com.arogyamed.healthcare.dto.AuditLogRequestDTO;
import com.arogyamed.healthcare.dto.AuditLogResponseDTO;
import com.arogyamed.healthcare.model.*;
import com.arogyamed.healthcare.repository.AuditLogRepository;
import com.arogyamed.healthcare.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    // =========================
    // CREATE AUDIT LOG
    // =========================

    @Override
    public AuditLogResponseDTO createAuditLog(AuditLogRequestDTO requestDTO) {

        AuditLog auditLog = new AuditLog();

        auditLog.setUserId(requestDTO.getUserId());

        auditLog.setUserName(requestDTO.getUserName());

        auditLog.setRole(requestDTO.getRole());

        auditLog.setModuleName(requestDTO.getModuleName());

        auditLog.setActionType(requestDTO.getActionType());

        auditLog.setActionStatus(requestDTO.getActionStatus());

        auditLog.setActionDescription(requestDTO.getActionDescription());

        auditLog.setEntityName(requestDTO.getEntityName());

        auditLog.setEntityId(requestDTO.getEntityId());

        auditLog.setIpAddress(requestDTO.getIpAddress());

        auditLog.setRequestUrl(requestDTO.getRequestUrl());

        auditLog.setHttpMethod(requestDTO.getHttpMethod());

        auditLog.setUserAgent(requestDTO.getUserAgent());

        auditLog.setRemarks(requestDTO.getRemarks());


        AuditLog savedAuditLog = auditLogRepository.save(auditLog);

        return convertToResponseDTO(savedAuditLog);
    }

    // =========================
    // GET BY ID
    // =========================

    @Override
    public AuditLogResponseDTO getAuditLogById(Long id) {

        AuditLog auditLog = auditLogRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Audit Log not found"));

        return convertToResponseDTO(auditLog);
    }

    // =========================
    // GET ALL
    // =========================

    @Override
    public List<AuditLogResponseDTO> getAllAuditLogs() {

        return auditLogRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // DELETE
    // =========================

    @Override
    public void deleteAuditLog(Long id) {

        auditLogRepository.deleteById(id);

    }

    // =========================
    // SEARCH BY USER
    // =========================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByUserId(Long userId) {

        return auditLogRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // SEARCH BY ROLE
    // =========================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByRole(String role) {

        return auditLogRepository.findByRole(role)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

    }

    // =========================
    // SEARCH BY MODULE
    // =========================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByModuleName(String moduleName) {

        return auditLogRepository.findByModuleName(moduleName)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

    }

    // =========================
    // SEARCH BY ACTION TYPE
    // =========================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByActionType(ActionType actionType) {

        return auditLogRepository.findByActionType(actionType)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

    }

    // =========================
    // SEARCH BY ACTION STATUS
    // =========================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByActionStatus(ActionStatus actionStatus) {

        return auditLogRepository.findByActionStatus(actionStatus)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

    }

    // =========================
    // SEARCH BETWEEN DATES
    // =========================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsBetweenDates(
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {

        return auditLogRepository.findByActionTimeBetween(startDate, endDate)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

    }

    // =========================
    // RECENT AUDIT LOGS
    // =========================

    @Override
    public List<AuditLogResponseDTO> getRecentAuditLogs() {

        return auditLogRepository.findTop20ByOrderByActionTimeDesc()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

    }

    // =========================
    // AUDIT DASHBOARD
    // =========================

    @Override
    public AuditDashboardDTO getAuditDashboard() {

        AuditDashboardDTO dto = new AuditDashboardDTO();

        // =========================
        // TOTAL LOGS
        // =========================

        dto.setTotalLogs(auditLogRepository.count());

        // =========================
        // STATUS ANALYTICS
        // =========================

        dto.setSuccessfulActions(auditLogRepository.countByActionStatus(ActionStatus.SUCCESS));

        dto.setFailedActions(auditLogRepository.countByActionStatus(ActionStatus.FAILED));

        dto.setWarningActions(auditLogRepository.countByActionStatus(ActionStatus.WARNING));

        // =========================
        // MODULE ANALYTICS
        // =========================

        dto.setLoginCount(auditLogRepository.countByActionType(ActionType.LOGIN));

        dto.setOrderCount(auditLogRepository.countByActionType(ActionType.ORDER_CREATED));

        dto.setPaymentCount(auditLogRepository.countByActionType(ActionType.PAYMENT_COMPLETED));

        dto.setMedicineCount(auditLogRepository.countByActionType(ActionType.MEDICINE_CREATED));

        dto.setInventoryCount(auditLogRepository.countByModuleName("Inventory"));

        dto.setAppointmentCount(auditLogRepository.countByActionType(ActionType.APPOINTMENT_BOOKED));

        return dto;

    }

    // =========================
    // ENTITY TO DTO CONVERSION
    // =========================

    private AuditLogResponseDTO convertToResponseDTO(AuditLog auditLog) {

        AuditLogResponseDTO dto = new AuditLogResponseDTO();

        dto.setId(auditLog.getId());

        dto.setUserId(auditLog.getUserId());

        dto.setUserName(auditLog.getUserName());

        dto.setRole(auditLog.getRole());

        dto.setModuleName(auditLog.getModuleName());

        dto.setActionType(auditLog.getActionType());

        dto.setActionStatus(auditLog.getActionStatus());

        dto.setActionDescription(auditLog.getActionDescription());

        dto.setEntityName(auditLog.getEntityName());

        dto.setEntityId(auditLog.getEntityId());

        dto.setIpAddress(auditLog.getIpAddress());

        dto.setRequestUrl(auditLog.getRequestUrl());

        dto.setHttpMethod(auditLog.getHttpMethod());

        dto.setUserAgent(auditLog.getUserAgent());

        dto.setRemarks(auditLog.getRemarks());

        dto.setActionTime(auditLog.getActionTime());

        return dto;

    }

}
