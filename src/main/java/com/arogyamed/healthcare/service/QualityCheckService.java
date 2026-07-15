package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.QualityCheckRequestDTO;
import com.arogyamed.healthcare.dto.QualityCheckResponseDTO;

import java.util.List;

public interface QualityCheckService {

    QualityCheckResponseDTO createQualityCheck(QualityCheckRequestDTO requestDTO);

    QualityCheckResponseDTO getQualityCheckById(Long id);

    List<QualityCheckResponseDTO> getAllQualityChecks();

    QualityCheckResponseDTO updateQualityCheck(Long id, QualityCheckRequestDTO requestDTO);

    void deleteQualityCheck(Long id);

}
