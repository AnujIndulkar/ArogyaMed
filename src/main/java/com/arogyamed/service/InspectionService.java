package com.arogyamed.service;

import com.arogyamed.dto.InspectionDTO;
import com.arogyamed.dto.QualityCheckResponseDTO;

import java.util.List;

public interface InspectionService {

    List<InspectionDTO.PendingBatch> pendingBatches(String search, int limit);

    List<QualityCheckResponseDTO> myInspections(String inspectorEmail);

    InspectionDTO.Summary summary(String inspectorEmail);

    QualityCheckResponseDTO submit(String inspectorEmail, InspectionDTO.SubmitRequest request);
}