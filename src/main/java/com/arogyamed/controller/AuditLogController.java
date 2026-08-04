package com.arogyamed.controller;

import com.arogyamed.dto.AuditDashboardDTO;
import com.arogyamed.dto.AuditLogRequestDTO;
import com.arogyamed.dto.AuditLogResponseDTO;
import com.arogyamed.model.ActionStatus;
import com.arogyamed.model.ActionType;
import com.arogyamed.model.Role;
import com.arogyamed.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@CrossOrigin
public class AuditLogController {

    private final AuditLogService auditLogService;

    // ==========================================================
    // CREATE AUDIT LOG
    // ==========================================================

    @PostMapping
    public AuditLogResponseDTO createAuditLog(@RequestBody AuditLogRequestDTO requestDTO) {

        return auditLogService.createAuditLog(requestDTO);
    }

    // ==========================================================
    // GET AUDIT LOG BY ID
    // ==========================================================

    @GetMapping("/{id}")
    public AuditLogResponseDTO getAuditLogById(@PathVariable Long id) {

        return auditLogService.getAuditLogById(id);
    }

    // ==========================================================
    // GET ALL AUDIT LOGS
    // ==========================================================

    @GetMapping
    public List<AuditLogResponseDTO> getAllAuditLogs() {

        return auditLogService.getAllAuditLogs();
    }

    // ==========================================================
    // DELETE AUDIT LOG
    // ==========================================================

    @DeleteMapping("/{id}")
    public String deleteAuditLog(@PathVariable Long id) {

        auditLogService.deleteAuditLog(id);

        return "Audit Log deleted successfully.";
    }

    // ==========================================================
    // RECENT AUDIT LOGS
    // ==========================================================

    @GetMapping("/recent")
    public List<AuditLogResponseDTO> getRecentAuditLogs() {

        return auditLogService.getRecentAuditLogs();
    }

    // ==========================================================
    // AUDIT DASHBOARD
    // ==========================================================

    @GetMapping("/dashboard")
    public AuditDashboardDTO getAuditDashboard() {

        return auditLogService.getAuditDashboard();
    }

    // ==========================================================
    // SEARCH BY USER ID
    // ==========================================================

    @GetMapping("/search/user")
    public List<AuditLogResponseDTO> getAuditLogsByUserId(@RequestParam Long userId) {

        return auditLogService.getAuditLogsByUserId(userId);
    }

    // ==========================================================
    // SEARCH BY USER NAME
    // ==========================================================

    @GetMapping("/search/user-name")
    public List<AuditLogResponseDTO> getAuditLogsByUserName(@RequestParam String userName) {

        return auditLogService.getAuditLogsByUserName(userName);
    }

    // ==========================================================
    // SEARCH BY ROLE
    // ==========================================================

    @GetMapping("/search/role")
    public List<AuditLogResponseDTO> getAuditLogsByRole(@RequestParam Role role) {

        return auditLogService.getAuditLogsByRole(role);
    }

    // ==========================================================
    // SEARCH BY MODULE
    // ==========================================================

    @GetMapping("/search/module")
    public List<AuditLogResponseDTO> getAuditLogsByModuleName(@RequestParam String moduleName) {

        return auditLogService.getAuditLogsByModuleName(moduleName);
    }

    // ==========================================================
    // SEARCH BY ACTION TYPE
    // ==========================================================

    @GetMapping("/search/action-type")
    public List<AuditLogResponseDTO> getAuditLogsByActionType(@RequestParam ActionType actionType) {

        return auditLogService.getAuditLogsByActionType(actionType);
    }

    // ==========================================================
    // SEARCH BY ACTION STATUS
    // ==========================================================

    @GetMapping("/search/status")
    public List<AuditLogResponseDTO> getAuditLogsByActionStatus(@RequestParam ActionStatus actionStatus) {

        return auditLogService.getAuditLogsByActionStatus(actionStatus);
    }

    // ==========================================================
    // SEARCH BETWEEN DATES
    // ==========================================================

    @GetMapping("/search/date-range")
    public List<AuditLogResponseDTO> getAuditLogsBetweenDates(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {

        return auditLogService.getAuditLogsBetweenDates(startDate, endDate);
    }

    // ==========================================================
    // SEARCH BY ROLE + STATUS
    // ==========================================================

    @GetMapping("/search/role-status")
    public List<AuditLogResponseDTO> getAuditLogsByRoleAndStatus(@RequestParam Role role, @RequestParam ActionStatus actionStatus) {

        return auditLogService.getAuditLogsByRoleAndStatus(role, actionStatus);
    }

    // ==========================================================
    // SEARCH BY ROLE + ACTION TYPE
    // ==========================================================

    @GetMapping("/search/role-action")
    public List<AuditLogResponseDTO> getAuditLogsByRoleAndActionType(@RequestParam Role role, @RequestParam ActionType actionType) {

        return auditLogService.getAuditLogsByRoleAndActionType(role, actionType);
    }

    // ==========================================================
    // SEARCH BY MODULE + STATUS
    // ==========================================================

    @GetMapping("/search/module-status")
    public List<AuditLogResponseDTO> getAuditLogsByModuleAndStatus(@RequestParam String moduleName, @RequestParam ActionStatus actionStatus) {

        return auditLogService.getAuditLogsByModuleAndStatus(moduleName, actionStatus);
    }

    // ==========================================================
    // SEARCH BY ENTITY NAME
    // ==========================================================

    @GetMapping("/search/entity-name")
    public List<AuditLogResponseDTO> getAuditLogsByEntityName(@RequestParam String entityName) {

        return auditLogService.getAuditLogsByEntityName(entityName);
    }

    // ==========================================================
    // SEARCH BY ENTITY ID
    // ==========================================================

    @GetMapping("/search/entity-id")
    public List<AuditLogResponseDTO> getAuditLogsByEntityId(@RequestParam Long entityId) {

        return auditLogService.getAuditLogsByEntityId(entityId);
    }

    // ==========================================================
    // SEARCH BY IP ADDRESS
    // ==========================================================

    @GetMapping("/search/ip")
    public List<AuditLogResponseDTO> getAuditLogsByIpAddress(@RequestParam String ipAddress) {

        return auditLogService.getAuditLogsByIpAddress(ipAddress);
    }

    // ==========================================================
    // SEARCH BY REQUEST URL
    // ==========================================================

    @GetMapping("/search/request-url")
    public List<AuditLogResponseDTO> getAuditLogsByRequestUrl(@RequestParam String requestUrl) {

        return auditLogService.getAuditLogsByRequestUrl(requestUrl);
    }

    // ==========================================================
    // SEARCH BY HTTP METHOD
    // ==========================================================

    @GetMapping("/search/http-method")
    public List<AuditLogResponseDTO> getAuditLogsByHttpMethod(@RequestParam String httpMethod) {

        return auditLogService.getAuditLogsByHttpMethod(httpMethod);
    }

    // ==========================================================
    // SEARCH BY BROWSER
    // ==========================================================

    @GetMapping("/search/browser")
    public List<AuditLogResponseDTO> getAuditLogsByBrowser(@RequestParam String browser) {

        return auditLogService.getAuditLogsByBrowser(browser);
    }

    // ==========================================================
    // SEARCH BY OPERATING SYSTEM
    // ==========================================================

    @GetMapping("/search/os")
    public List<AuditLogResponseDTO> getAuditLogsByOperatingSystem(@RequestParam String operatingSystem) {

        return auditLogService.getAuditLogsByOperatingSystem(operatingSystem);
    }

    // ==========================================================
    // SEARCH BY RESPONSE STATUS
    // ==========================================================

    @GetMapping("/search/response-status")
    public List<AuditLogResponseDTO> getAuditLogsByResponseStatus(@RequestParam Integer responseStatus) {

        return auditLogService.getAuditLogsByResponseStatus(responseStatus);
    }

    // ==========================================================
    // SEARCH BY SUCCESS
    // ==========================================================

    @GetMapping("/search/success")
    public List<AuditLogResponseDTO> getAuditLogsBySuccess(@RequestParam Boolean success) {

        return auditLogService.getAuditLogsBySuccess(success);
    }

    // ==========================================================
    // SEARCH BY USER NAME + MODULE
    // ==========================================================

    @GetMapping("/search/user-module")
    public List<AuditLogResponseDTO> getAuditLogsByUserNameAndModule(@RequestParam String userName, @RequestParam String moduleName) {

        return auditLogService.getAuditLogsByUserNameAndModule(userName, moduleName);
    }

}
