package com.arogyamed.dto;

import com.arogyamed.model.ReportFormat;
import com.arogyamed.model.ReportType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRequestDto {

    private String reportName;

    private ReportType reportType;

    private ReportFormat reportFormat;

    private Long generatedById;

    private LocalDate startDate;

    private LocalDate endDate;
}
