package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.ReportRequestDto;
import com.arogyamed.healthcare.dto.ReportResponseDto;
import com.arogyamed.healthcare.exception.ReportGenerationException;
import com.arogyamed.healthcare.model.Report;
import com.arogyamed.healthcare.model.ReportType;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.ReportRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final UserRepository userRepository;

    @Override
    public ReportResponseDto generateReport(ReportRequestDto reportRequestDto) {

        User generatedBy = userRepository.findById(reportRequestDto.getGeneratedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = Report.builder()
                .reportName(reportRequestDto.getReportName())
                .reportType(reportRequestDto.getReportType())
                .reportFormat(reportRequestDto.getReportFormat())
                .generatedBy(generatedBy)
                .generatedAt(LocalDateTime.now())
                .startDate(reportRequestDto.getStartDate())
                .endDate(reportRequestDto.getEndDate())
                .filePath("")
                .status("GENERATED")
                .build();

        Report savedReport = reportRepository.save(report);

        return ReportResponseDto.builder()
                .id(savedReport.getId())
                .reportName(savedReport.getReportName())
                .reportType(savedReport.getReportType())
                .reportFormat(savedReport.getReportFormat())
                .generatedBy(savedReport.getGeneratedBy().getFullName())
                .generatedAt(savedReport.getGeneratedAt())
                .startDate(savedReport.getStartDate())
                .endDate(savedReport.getEndDate())
                .filePath(savedReport.getFilePath())
                .status(savedReport.getStatus())
                .build();
    }

    @Override
    public ReportResponseDto getReportById(Long reportId) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        return ReportResponseDto.builder()
                .id(report.getId())
                .reportName(report.getReportName())
                .reportType(report.getReportType())
                .reportFormat(report.getReportFormat())
                .generatedBy(report.getGeneratedBy().getFullName())
                .generatedAt(report.getGeneratedAt())
                .startDate(report.getStartDate())
                .endDate(report.getEndDate())
                .filePath(report.getFilePath())
                .status(report.getStatus())
                .build();
    }

    @Override
    public List<ReportResponseDto> getAllReports() {

        return reportRepository.findAll()
                .stream()
                .map(report -> ReportResponseDto.builder()
                        .id(report.getId())
                        .reportName(report.getReportName())
                        .reportType(report.getReportType())
                        .reportFormat(report.getReportFormat())
                        .generatedBy(report.getGeneratedBy().getFullName())
                        .generatedAt(report.getGeneratedAt())
                        .startDate(report.getStartDate())
                        .endDate(report.getEndDate())
                        .filePath(report.getFilePath())
                        .status(report.getStatus())
                        .build())
                .toList();
    }

    @Override
    public List<ReportResponseDto> getReportsByType(String reportType) {

        return reportRepository.findByReportType(
                        ReportType.valueOf(reportType.toUpperCase()))
                .stream()
                .map(report -> ReportResponseDto.builder()
                        .id(report.getId())
                        .reportName(report.getReportName())
                        .reportType(report.getReportType())
                        .reportFormat(report.getReportFormat())
                        .generatedBy(report.getGeneratedBy().getFullName())
                        .generatedAt(report.getGeneratedAt())
                        .startDate(report.getStartDate())
                        .endDate(report.getEndDate())
                        .filePath(report.getFilePath())
                        .status(report.getStatus())
                        .build())
                .toList();
    }

    @Override
    public List<ReportResponseDto> getReportsByStatus(String status) {

        return reportRepository.findByStatus(status)
                .stream()
                .map(report -> ReportResponseDto.builder()
                        .id(report.getId())
                        .reportName(report.getReportName())
                        .reportType(report.getReportType())
                        .reportFormat(report.getReportFormat())
                        .generatedBy(report.getGeneratedBy().getFullName())
                        .generatedAt(report.getGeneratedAt())
                        .startDate(report.getStartDate())
                        .endDate(report.getEndDate())
                        .filePath(report.getFilePath())
                        .status(report.getStatus())
                        .build())
                .toList();
    }

    @Override
    public void deleteReport(Long reportId) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        reportRepository.delete(report);
    }

}
