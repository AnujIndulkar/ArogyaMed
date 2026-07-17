package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.ReportRequestDto;
import com.arogyamed.healthcare.dto.ReportResponseDto;
import com.arogyamed.healthcare.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDto> generateReport(@RequestBody ReportRequestDto reportRequestDto) {

        return new ResponseEntity<>(reportService.generateReport(reportRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponseDto> getReportById(@PathVariable Long reportId) {

        return ResponseEntity.ok(reportService.getReportById(reportId));
    }

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getAllReports() {

        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/type/{reportType}")
    public ResponseEntity<List<ReportResponseDto>> getReportsByType(@PathVariable String reportType) {

        return ResponseEntity.ok(reportService.getReportsByType(reportType));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportResponseDto>> getReportsByStatus(@PathVariable String status) {

        return ResponseEntity.ok(reportService.getReportsByStatus(status));
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<String> deleteReport(@PathVariable Long reportId) {

        reportService.deleteReport(reportId);

        return ResponseEntity.ok("Report deleted successfully.");
    }

}