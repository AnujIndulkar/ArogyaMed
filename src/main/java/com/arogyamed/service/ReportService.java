package com.arogyamed.service;

import com.arogyamed.dto.ReportRequestDto;
import com.arogyamed.dto.ReportResponseDto;

import java.util.List;

public interface ReportService {

    ReportResponseDto generateReport(ReportRequestDto reportRequestDto);

    ReportResponseDto getReportById(Long reportId);

    List<ReportResponseDto> getAllReports();

    List<ReportResponseDto> getReportsByType(String reportType);

    List<ReportResponseDto> getReportsByStatus(String status);

    void deleteReport(Long reportId);
}
