package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.SymptomCheckRequestDTO;
import com.arogyamed.healthcare.dto.SymptomCheckResponseDTO;

public interface AISymptomCheckerService {

    SymptomCheckResponseDTO checkSymptoms(SymptomCheckRequestDTO requestDTO);
}
