package com.arogyamed.controller;

import com.arogyamed.dto.SymptomCheckRequestDTO;
import com.arogyamed.dto.SymptomCheckResponseDTO;
import com.arogyamed.service.AISymptomCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/symptom-checker")
@RequiredArgsConstructor
public class AISymptomCheckerController {

    private final AISymptomCheckerService aiSymptomCheckerService;

    @PostMapping
    public SymptomCheckResponseDTO checkSymptoms(@RequestBody SymptomCheckRequestDTO requestDTO) {

        return aiSymptomCheckerService.checkSymptoms(requestDTO);
    }
}
