package com.arogyamed.dto;

import com.arogyamed.model.QualityStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityCheckResponseDTO {

    private Long id;

    private Long medicineId;

    private String medicineName;

    private Long companyId;

    private String companyName;

    private Long inspectorId;

    private String inspectorName;

    private String batchNumber;

    private boolean packagingVerified;

    private boolean sealVerified;

    private boolean temperatureVerified;

    private boolean expiryVerified;

    private String inspectorRemarks;

    private LocalDate inspectionDate;

    private QualityStatus qualityStatus;

}
