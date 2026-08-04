package com.arogyamed.dto;

import com.arogyamed.model.ReportFormat;
import com.arogyamed.model.ReportType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponseDto {

    private Long id;

    private String reportName;

    private ReportType reportType;

    private ReportFormat reportFormat;

    private String generatedBy;

    private LocalDateTime generatedAt;

    private LocalDate startDate;

    private LocalDate endDate;

    private String filePath;

    private String status;
}
