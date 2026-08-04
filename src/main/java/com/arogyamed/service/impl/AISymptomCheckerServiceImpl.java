package com.arogyamed.service.impl;

import com.arogyamed.dto.DoctorResponseDTO;
import com.arogyamed.dto.SymptomCheckRequestDTO;
import com.arogyamed.dto.SymptomCheckResponseDTO;
import com.arogyamed.model.Doctor;
import com.arogyamed.model.UrgencyLevel;
import com.arogyamed.repository.DoctorRepository;
import com.arogyamed.service.AISymptomCheckerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AISymptomCheckerServiceImpl implements AISymptomCheckerService {

    private static final String DISCLAIMER =
            "This is an AI-generated suggestion, not a medical diagnosis. " +
                    "Please consult a qualified doctor for accurate diagnosis and treatment. " +
                    "If this is a medical emergency, contact emergency services immediately.";

    private final DoctorRepository doctorRepository;

    private final RestTemplate restTemplate;

    @Value("${ai.api.enabled:false}")
    private boolean aiEnabled;

    @Value("${ai.api.url:}")
    private String aiApiUrl;

    @Value("${ai.api.key:}")
    private String aiApiKey;

    @Value("${ai.api.model:gpt-4o-mini}")
    private String aiModel;

    @Override
    public SymptomCheckResponseDTO checkSymptoms(SymptomCheckRequestDTO requestDTO) {

        if (requestDTO.getSymptoms() == null || requestDTO.getSymptoms().isBlank()) {
            throw new RuntimeException("Symptoms description is required.");
        }

        AnalysisResult result;

        if (aiEnabled && aiApiKey != null && !aiApiKey.isBlank()) {

            try {
                result = analyzeWithAI(requestDTO);
            } catch (Exception e) {
                System.out.println("AI symptom analysis failed, falling back to rule engine: " + e.getMessage());
                result = analyzeWithRules(requestDTO);
            }

        } else {
            result = analyzeWithRules(requestDTO);
        }

        List<Doctor> matchedDoctors =
                doctorRepository.findBySpecializationContainingIgnoreCase(result.specialization);

        List<DoctorResponseDTO> doctorDTOs = new ArrayList<>();

        for (Doctor doctor : matchedDoctors) {

            DoctorResponseDTO dto = new DoctorResponseDTO();

            dto.setId(doctor.getId());
            dto.setUserId(doctor.getUser().getId());
            dto.setFullName(doctor.getUser().getFullName());
            dto.setEmail(doctor.getUser().getEmail());
            dto.setSpecialization(doctor.getSpecialization());
            dto.setQualification(doctor.getQualification());
            dto.setExperienceYears(doctor.getExperienceYears());
            dto.setLicenseNumber(doctor.getLicenseNumber());
            dto.setHospitalName(doctor.getHospitalName());
            dto.setConsultationFee(doctor.getConsultationFee());

            doctorDTOs.add(dto);
        }

        return SymptomCheckResponseDTO.builder()
                .inputSymptoms(requestDTO.getSymptoms())
                .possibleConditions(result.possibleConditions)
                .recommendedSpecialization(result.specialization)
                .urgencyLevel(result.urgencyLevel)
                .recommendedDoctors(doctorDTOs)
                .aiGenerated(result.aiGenerated)
                .disclaimer(DISCLAIMER)
                .build();
    }

    // ==========================================================
    // AI-BASED ANALYSIS (OpenAI-compatible chat completions API)
    // ==========================================================

    private AnalysisResult analyzeWithAI(SymptomCheckRequestDTO requestDTO) throws Exception {

        String prompt = buildPrompt(requestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(aiApiKey);

        String requestBody = """
                {
                  "model": "%s",
                  "messages": [
                    {"role": "system", "content": "You are a medical triage assistant. Always respond with strict JSON only, no extra text."},
                    {"role": "user", "content": %s}
                  ],
                  "temperature": 0.2
                }
                """.formatted(aiModel, new ObjectMapper().writeValueAsString(prompt));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String rawResponse = restTemplate.postForObject(aiApiUrl, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(rawResponse);

        String content = root.path("choices").get(0).path("message").path("content").asText();

        JsonNode parsed = mapper.readTree(content);

        AnalysisResult result = new AnalysisResult();

        result.specialization = parsed.path("recommendedSpecialization").asText("General Physician");
        result.urgencyLevel = UrgencyLevel.valueOf(
                parsed.path("urgencyLevel").asText("MEDIUM").toUpperCase(Locale.ROOT));

        List<String> conditions = new ArrayList<>();
        parsed.path("possibleConditions").forEach(node -> conditions.add(node.asText()));
        result.possibleConditions = conditions;

        result.aiGenerated = true;

        return result;
    }

    private String buildPrompt(SymptomCheckRequestDTO requestDTO) {

        return "Patient symptoms: " + requestDTO.getSymptoms()
                + (requestDTO.getAge() != null ? ". Age: " + requestDTO.getAge() : "")
                + (requestDTO.getGender() != null ? ". Gender: " + requestDTO.getGender() : "")
                + ". Respond ONLY with JSON in this exact shape: "
                + "{\"possibleConditions\": [\"...\"], \"recommendedSpecialization\": \"...\", \"urgencyLevel\": \"LOW|MEDIUM|HIGH|EMERGENCY\"}. "
                + "recommendedSpecialization must be a single common medical specialization "
                + "(e.g. General Physician, Cardiologist, Dermatologist, Neurologist, Orthopedic, ENT, Gynecologist, Pediatrician, Psychiatrist, Dentist, Ophthalmologist).";
    }

    // ==========================================================
    // RULE-BASED FALLBACK (used when AI is disabled / unreachable)
    // ==========================================================

    private AnalysisResult analyzeWithRules(SymptomCheckRequestDTO requestDTO) {

        String text = requestDTO.getSymptoms().toLowerCase(Locale.ROOT);

        AnalysisResult result = new AnalysisResult();
        result.aiGenerated = false;
        result.possibleConditions = new ArrayList<>();
        result.urgencyLevel = UrgencyLevel.LOW;
        result.specialization = "General Physician";

        // Emergency keywords checked first
        if (containsAny(text, "chest pain", "difficulty breathing", "can't breathe",
                "unconscious", "severe bleeding", "heart attack", "stroke", "seizure")) {

            result.urgencyLevel = UrgencyLevel.EMERGENCY;
            result.specialization = "General Physician";
            result.possibleConditions.add("Potential medical emergency");
            return result;
        }

        if (containsAny(text, "chest", "palpitation", "heart", "blood pressure")) {

            result.specialization = "Cardiologist";
            result.urgencyLevel = UrgencyLevel.HIGH;
            result.possibleConditions.add("Possible cardiac-related condition");

        } else if (containsAny(text, "skin", "rash", "itching", "acne", "allergy")) {

            result.specialization = "Dermatologist";
            result.urgencyLevel = UrgencyLevel.LOW;
            result.possibleConditions.add("Possible dermatological condition");

        } else if (containsAny(text, "tooth", "teeth", "gum", "dental")) {

            result.specialization = "Dentist";
            result.urgencyLevel = UrgencyLevel.LOW;
            result.possibleConditions.add("Possible dental issue");

        } else if (containsAny(text, "eye", "vision", "blurry")) {

            result.specialization = "Ophthalmologist";
            result.urgencyLevel = UrgencyLevel.MEDIUM;
            result.possibleConditions.add("Possible eye-related condition");

        } else if (containsAny(text, "bone", "joint", "fracture", "sprain", "back pain")) {

            result.specialization = "Orthopedic";
            result.urgencyLevel = UrgencyLevel.MEDIUM;
            result.possibleConditions.add("Possible musculoskeletal condition");

        } else if (containsAny(text, "child", "infant", "baby")) {

            result.specialization = "Pediatrician";
            result.urgencyLevel = UrgencyLevel.MEDIUM;
            result.possibleConditions.add("Pediatric consultation recommended");

        } else if (containsAny(text, "pregnant", "pregnancy", "menstrual", "gynecological")) {

            result.specialization = "Gynecologist";
            result.urgencyLevel = UrgencyLevel.MEDIUM;
            result.possibleConditions.add("Possible gynecological condition");

        } else if (containsAny(text, "anxiety", "depression", "stress", "sleep", "mental")) {

            result.specialization = "Psychiatrist";
            result.urgencyLevel = UrgencyLevel.MEDIUM;
            result.possibleConditions.add("Possible mental health concern");

        } else if (containsAny(text, "headache", "migraine", "dizziness", "numbness")) {

            result.specialization = "Neurologist";
            result.urgencyLevel = UrgencyLevel.MEDIUM;
            result.possibleConditions.add("Possible neurological condition");

        } else if (containsAny(text, "fever", "cough", "cold", "flu", "sore throat")) {

            result.specialization = "General Physician";
            result.urgencyLevel = UrgencyLevel.LOW;
            result.possibleConditions.add("Possible common viral/bacterial infection");

        } else {

            result.specialization = "General Physician";
            result.urgencyLevel = UrgencyLevel.LOW;
            result.possibleConditions.add("General consultation recommended for accurate assessment");
        }

        return result;
    }

    private boolean containsAny(String text, String... keywords) {

        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }

        return false;
    }

    private static class AnalysisResult {
        String specialization;
        UrgencyLevel urgencyLevel;
        List<String> possibleConditions;
        Boolean aiGenerated;
    }
}
