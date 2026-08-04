package com.arogyamed.service.impl;

import com.arogyamed.dto.AuditDashboardDTO;
import com.arogyamed.dto.AuditLogRequestDTO;
import com.arogyamed.dto.AuditLogResponseDTO;
import com.arogyamed.model.*;
import com.arogyamed.model.ActionStatus;
import com.arogyamed.model.ActionType;
import com.arogyamed.model.AuditLog;
import com.arogyamed.model.Role;
import com.arogyamed.repository.AuditLogRepository;
import com.arogyamed.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    // ==========================================================
    // CREATE AUDIT LOG
    // ==========================================================

    @Override
    public AuditLogResponseDTO createAuditLog(AuditLogRequestDTO requestDTO) {

        AuditLog auditLog = new AuditLog();

        auditLog.setUserId(requestDTO.getUserId());
        auditLog.setUserName(requestDTO.getUserName());
        auditLog.setRole(requestDTO.getRole());

        auditLog.setModuleName(requestDTO.getModuleName());

        auditLog.setActionType(requestDTO.getActionType());
        auditLog.setActionStatus(requestDTO.getActionStatus());

        auditLog.setEntityName(requestDTO.getEntityName());
        auditLog.setEntityId(requestDTO.getEntityId());

        auditLog.setIpAddress(requestDTO.getIpAddress());
        auditLog.setRequestUrl(requestDTO.getRequestUrl());
        auditLog.setHttpMethod(requestDTO.getHttpMethod());

        auditLog.setUserAgent(requestDTO.getUserAgent());
        auditLog.setBrowser(requestDTO.getBrowser());
        auditLog.setOperatingSystem(requestDTO.getOperatingSystem());

        auditLog.setResponseStatus(requestDTO.getResponseStatus());
        auditLog.setSuccess(requestDTO.getSuccess());

        auditLog.setActionDescription(requestDTO.getActionDescription());
        auditLog.setRemarks(requestDTO.getRemarks());

        AuditLog savedAuditLog = auditLogRepository.save(auditLog);

        return convertToResponseDTO(savedAuditLog);
    }

    // ==========================================================
    // GET AUDIT LOG BY ID
    // ==========================================================

    @Override
    public AuditLogResponseDTO getAuditLogById(Long id) {

        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Audit Log not found with ID : " + id));

        return convertToResponseDTO(auditLog);
    }

    // ==========================================================
    // GET ALL AUDIT LOGS
    // ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAllAuditLogs() {

        return auditLogRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // DELETE AUDIT LOG
    // ==========================================================

    @Override
    public void deleteAuditLog(Long id) {

        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Audit Log not found with ID : " + id));

        auditLogRepository.delete(auditLog);
    }

    // ==========================================================
    // ENTITY → DTO
    // ==========================================================

    private AuditLogResponseDTO convertToResponseDTO(AuditLog auditLog) {

        AuditLogResponseDTO dto = new AuditLogResponseDTO();

        dto.setId(auditLog.getId());

        dto.setUserId(auditLog.getUserId());
        dto.setUserName(auditLog.getUserName());
        dto.setRole(auditLog.getRole());

        dto.setModuleName(auditLog.getModuleName());

        dto.setActionType(auditLog.getActionType());
        dto.setActionStatus(auditLog.getActionStatus());

        dto.setEntityName(auditLog.getEntityName());
        dto.setEntityId(auditLog.getEntityId());

        dto.setIpAddress(auditLog.getIpAddress());
        dto.setRequestUrl(auditLog.getRequestUrl());
        dto.setHttpMethod(auditLog.getHttpMethod());

        dto.setUserAgent(auditLog.getUserAgent());
        dto.setBrowser(auditLog.getBrowser());
        dto.setOperatingSystem(auditLog.getOperatingSystem());

        dto.setResponseStatus(auditLog.getResponseStatus());
        dto.setSuccess(auditLog.getSuccess());

        dto.setActionDescription(auditLog.getActionDescription());
        dto.setRemarks(auditLog.getRemarks());

        dto.setActionTime(auditLog.getActionTime());

        return dto;
    }

    // ==========================================================
// SEARCH BY USER ID
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByUserId(Long userId) {

        return auditLogRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY USER NAME
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByUserName(String userName) {

        return auditLogRepository.findByUserNameContainingIgnoreCase(userName)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY ROLE
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByRole(Role role) {

        return auditLogRepository.findByRole(role)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY ROLE & STATUS
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByRoleAndStatus(Role role, ActionStatus actionStatus) {

        return auditLogRepository.findByRoleAndActionStatus(role, actionStatus)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY ROLE & ACTION TYPE
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByRoleAndActionType(Role role, ActionType actionType) {

        return auditLogRepository.findByRoleAndActionType(role, actionType)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY MODULE NAME
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByModuleName(String moduleName) {

        return auditLogRepository.findByModuleNameContainingIgnoreCase(moduleName)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY MODULE & STATUS
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByModuleAndStatus(String moduleName, ActionStatus actionStatus) {

        return auditLogRepository.findByModuleNameAndActionStatus(
                        moduleName,
                        actionStatus)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY ACTION TYPE
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByActionType(ActionType actionType) {

        return auditLogRepository.findByActionType(actionType)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY ACTION STATUS
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByActionStatus(ActionStatus actionStatus) {

        return auditLogRepository.findByActionStatus(actionStatus)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // ==========================================================
// SEARCH BY ENTITY NAME
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByEntityName(String entityName) {

        return auditLogRepository.findByEntityNameContainingIgnoreCase(entityName)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY ENTITY ID
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByEntityId(Long entityId) {

        return auditLogRepository.findByEntityId(entityId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY IP ADDRESS
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByIpAddress(String ipAddress) {

        return auditLogRepository.findByIpAddress(ipAddress)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY HTTP METHOD
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByHttpMethod(String httpMethod) {

        return auditLogRepository.findByHttpMethod(httpMethod)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY REQUEST URL
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByRequestUrl(String requestUrl) {

        return auditLogRepository.findByRequestUrlContainingIgnoreCase(requestUrl)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY BROWSER
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByBrowser(String browser) {

        return auditLogRepository.findByBrowserContainingIgnoreCase(browser)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY OPERATING SYSTEM
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByOperatingSystem(String operatingSystem) {

        return auditLogRepository.findByOperatingSystemContainingIgnoreCase(operatingSystem)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY RESPONSE STATUS
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByResponseStatus(Integer responseStatus) {

        return auditLogRepository.findByResponseStatus(responseStatus)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BY SUCCESS
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsBySuccess(Boolean success) {

        return auditLogRepository.findBySuccess(success)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH BETWEEN DATES
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {

        return auditLogRepository.findByActionTimeBetween(startDate, endDate)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// SEARCH USER NAME + MODULE
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByUserNameAndModule(String userName, String moduleName) {

        return auditLogRepository
                .findByUserNameContainingIgnoreCaseAndModuleNameContainingIgnoreCase(userName, moduleName)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// RECENT AUDIT LOGS
// ==========================================================

    @Override
    public List<AuditLogResponseDTO> getRecentAuditLogs() {

        return auditLogRepository.findTop20ByOrderByActionTimeDesc()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

// ==========================================================
// DASHBOARD
// ==========================================================

    @Override
    public AuditDashboardDTO getAuditDashboard() {

        AuditDashboardDTO dto = new AuditDashboardDTO();

        dto.setTotalLogs(auditLogRepository.count());

        dto.setSuccessfulActions(auditLogRepository.countByActionStatus(ActionStatus.SUCCESS));

        dto.setFailedActions(auditLogRepository.countByActionStatus(ActionStatus.FAILED));

        dto.setWarningActions(auditLogRepository.countByActionStatus(ActionStatus.WARNING));

        dto.setLoginCount(auditLogRepository.countByActionType(ActionType.LOGIN));

        dto.setOrderCount(auditLogRepository.countByActionType(ActionType.ORDER_CREATED));

        dto.setPaymentCount(auditLogRepository.countByActionType(ActionType.PAYMENT_COMPLETED));

        dto.setMedicineCount(auditLogRepository.countByActionType(ActionType.MEDICINE_CREATED));

        dto.setInventoryCount(auditLogRepository.countByModuleName("Inventory"));

        dto.setAppointmentCount(auditLogRepository.countByActionType(ActionType.APPOINTMENT_BOOKED));

        return dto;
    }

}