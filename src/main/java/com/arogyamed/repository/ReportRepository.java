package com.arogyamed.repository;

import com.arogyamed.model.Report;
import com.arogyamed.model.ReportType;
import com.arogyamed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

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