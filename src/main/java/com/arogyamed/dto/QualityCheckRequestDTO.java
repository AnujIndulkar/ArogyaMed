package com.arogyamed.dto;

import com.arogyamed.model.QualityStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityCheckRequestDTO {

    private Long medicineId;

    private Long companyId;

    private Long adminId;

    private String batchNumber;

    private boolean packagingVerified;

    private boolean sealVerified;

    private boolean temperatureVerified;

    private boolean expiryVerified;

    private String inspectorRemarks;

    private LocalDate inspectionDate;

    private QualityStatus qualityStatus;

}
