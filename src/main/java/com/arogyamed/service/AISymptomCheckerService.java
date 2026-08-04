package com.arogyamed.service;

import com.arogyamed.dto.SymptomCheckRequestDTO;
import com.arogyamed.dto.SymptomCheckResponseDTO;

public interface AISymptomCheckerService {

    SymptomCheckResponseDTO checkSymptoms(SymptomCheckRequestDTO requestDTO);
}
