package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Report;
import com.arogyamed.healthcare.model.ReportType;
import com.arogyamed.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyamed.healthcare.model.ReportFormat;
import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReportType(ReportType reportType);

    List<Report> findByGeneratedBy(User generatedBy);

    List<Report> findByStatus(String status);

    List<Report> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Report> findByEndDateBetween(LocalDate startDate, LocalDate endDate);

    List<Report> findByReportNameContainingIgnoreCase(String reportName);
}