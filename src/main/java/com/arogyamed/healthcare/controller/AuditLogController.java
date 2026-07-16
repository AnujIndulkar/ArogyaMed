package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.AuditDashboardDTO;
import com.arogyamed.healthcare.dto.AuditLogRequestDTO;
import com.arogyamed.healthcare.dto.AuditLogResponseDTO;
import com.arogyamed.healthcare.model.ActionStatus;
import com.arogyamed.healthcare.model.ActionType;
import com.arogyamed.healthcare.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    // =========================
    // CREATE AUDIT LOG
    // =========================

    @PostMapping
    public AuditLogResponseDTO createAuditLog(@RequestBody AuditLogRequestDTO requestDTO){

        return auditLogService.createAuditLog(requestDTO);

    }

    // =========================
    // GET ALL AUDIT LOGS
    // =========================

    @GetMapping
    public List<AuditLogResponseDTO> getAllAuditLogs(){

        return auditLogService.getAllAuditLogs();

    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    public AuditLogResponseDTO getAuditLogById(@PathVariable Long id){

        return auditLogService.getAuditLogById(id);

    }

    // =========================
    // DELETE AUDIT LOG
    // =========================

    @DeleteMapping("/{id}")
    public String deleteAuditLog(@PathVariable Long id){

        auditLogService.deleteAuditLog(id);

        return "Audit Log Deleted Successfully";

    }

    // =========================
    // SEARCH BY USER
    // =========================

    @GetMapping("/user/{userId}")
    public List<AuditLogResponseDTO> getAuditLogsByUserId(@PathVariable Long userId){

        return auditLogService.getAuditLogsByUserId(userId);

    }

    // =========================
    // SEARCH BY ROLE
    // =========================

    @GetMapping("/role/{role}")
    public List<AuditLogResponseDTO> getAuditLogsByRole(@PathVariable String role){

        return auditLogService.getAuditLogsByRole(role);

    }

    // =========================
    // SEARCH BY MODULE
    // =========================

    @GetMapping("/module/{moduleName}")
    public List<AuditLogResponseDTO> getAuditLogsByModuleName(@PathVariable String moduleName){

        return auditLogService.getAuditLogsByModuleName(moduleName);

    }

    // =========================
    // SEARCH BY ACTION TYPE
    // =========================

    @GetMapping("/action/{actionType}")
    public List<AuditLogResponseDTO> getAuditLogsByActionType(@PathVariable ActionType actionType){

        return auditLogService.getAuditLogsByActionType(actionType);

    }

    // =========================
    // SEARCH BY STATUS
    // =========================

    @GetMapping("/status/{status}")
    public List<AuditLogResponseDTO> getAuditLogsByActionStatus(@PathVariable ActionStatus status){

        return auditLogService.getAuditLogsByActionStatus(status);

    }

    // =========================
    // DATE RANGE SEARCH
    // =========================

    @GetMapping("/date-range")
    public List<AuditLogResponseDTO> getAuditLogsBetweenDates(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate){

        return auditLogService.getAuditLogsBetweenDates(startDate, endDate
        );

    }

    // =========================
    // RECENT ACTIVITIES
    // =========================

    @GetMapping("/recent")
    public List<AuditLogResponseDTO> getRecentAuditLogs(){

        return auditLogService.getRecentAuditLogs();

    }

    // =========================
    // AUDIT DASHBOARD
    // =========================

    @GetMapping("/dashboard")
    public AuditDashboardDTO getAuditDashboard(){

        return auditLogService.getAuditDashboard();

    }

}
