package com.arogyamed.service.impl;

import com.arogyamed.dto.ReportRequestDto;
import com.arogyamed.dto.ReportResponseDto;
import com.arogyamed.model.Report;
import com.arogyamed.model.ReportType;
import com.arogyamed.model.User;
import com.arogyamed.repository.ReportRepository;
import com.arogyamed.repository.UserRepository;
import com.arogyamed.service.ReportService;
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
