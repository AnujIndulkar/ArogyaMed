package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.PatientRequestDTO;
import com.arogyamed.healthcare.dto.PatientResponseDTO;
import com.arogyamed.healthcare.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public PatientResponseDTO create(@Valid @RequestBody PatientRequestDTO request) {
        return patientService.createPatient(request);
    }

    @GetMapping("/{userId}")
    public PatientResponseDTO get(@PathVariable Long userId) {
        return patientService.getPatientByUserId(userId);
    }

    @PutMapping("/{userId}")
    public PatientResponseDTO update(@PathVariable Long userId, @Valid @RequestBody PatientRequestDTO request){
        return patientService.updatePatient(userId, request);
    }

    // ================= Search =================

    // Search by Name
    @GetMapping("/search/name")
    public List<PatientResponseDTO> searchByName(@RequestParam String name) {
        return patientService.searchByName(name);
    }

    // Search by Email
    @GetMapping("/search/email")
    public List<PatientResponseDTO> searchByEmail(@RequestParam String email) {
        return patientService.searchByEmail(email);
    }

    // Search by Phone Number
    @GetMapping("/search/phone")
    public List<PatientResponseDTO> searchByPhone(@RequestParam String phoneNumber) {
        return patientService.searchByPhone(phoneNumber);
    }

    // Search by Gender
    @GetMapping("/search/gender")
    public List<PatientResponseDTO> searchByGender(@RequestParam String gender) {
        return patientService.searchByGender(gender);
    }

    // Search by Blood Group
    @GetMapping("/search/blood-group")
    public List<PatientResponseDTO> searchByBloodGroup(@RequestParam String bloodGroup) {
        return patientService.searchByBloodGroup(bloodGroup);
    }

    // Search by Age
    @GetMapping("/search/age")
    public List<PatientResponseDTO> searchByAge(@RequestParam Integer age) {
        return patientService.searchByAge(age);
    }

    // Search by Date of Birth
    @GetMapping("/search/date-of-birth")
    public List<PatientResponseDTO> searchByDateOfBirth(@RequestParam LocalDate dateOfBirth) {
        return patientService.searchByDateOfBirth(dateOfBirth);
    }

    // Search by Allergies
    @GetMapping("/search/allergies")
    public List<PatientResponseDTO> searchByAllergies(@RequestParam String allergies) {
        return patientService.searchByAllergies(allergies);
    }

    // Search by Medical History
    @GetMapping("/search/medical-history")
    public List<PatientResponseDTO> searchByMedicalHistory(@RequestParam String medicalHistory) {
        return patientService.searchByMedicalHistory(medicalHistory);
    }

    // Search by Emergency Contact Name
    @GetMapping("/search/emergency-contact-name")
    public List<PatientResponseDTO> searchByEmergencyContactName(
            @RequestParam String emergencyContactName) {
        return patientService.searchByEmergencyContactName(emergencyContactName);
    }

    // Search by Emergency Contact Number
    @GetMapping("/search/emergency-contact-number")
    public List<PatientResponseDTO> searchByEmergencyContactNumber(
            @RequestParam String emergencyContactNumber) {
        return patientService.searchByEmergencyContactNumber(emergencyContactNumber);
    }

    // Search by Insurance Provider
    @GetMapping("/search/insurance-provider")
    public List<PatientResponseDTO> searchByInsuranceProvider(
            @RequestParam String insuranceProvider) {
        return patientService.searchByInsuranceProvider(insuranceProvider);
    }

    // Search by Insurance Policy Number
    @GetMapping("/search/insurance-policy")
    public List<PatientResponseDTO> searchByInsurancePolicyNumber(
            @RequestParam String insurancePolicyNumber) {
        return patientService.searchByInsurancePolicyNumber(insurancePolicyNumber);
    }

    // Search by Occupation
    @GetMapping("/search/occupation")
    public List<PatientResponseDTO> searchByOccupation(@RequestParam String occupation) {
        return patientService.searchByOccupation(occupation);
    }

    // Search by Marital Status
    @GetMapping("/search/marital-status")
    public List<PatientResponseDTO> searchByMaritalStatus(@RequestParam String maritalStatus) {
        return patientService.searchByMaritalStatus(maritalStatus);
    }

    // Search by City
    @GetMapping("/search/city")
    public List<PatientResponseDTO> searchByCity(@RequestParam String city) {
        return patientService.searchByCity(city);
    }

    // Search by District
    @GetMapping("/search/district")
    public List<PatientResponseDTO> searchByDistrict(@RequestParam String district) {
        return patientService.searchByDistrict(district);
    }

    // Search by State
    @GetMapping("/search/state")
    public List<PatientResponseDTO> searchByState(@RequestParam String state) {
        return patientService.searchByState(state);
    }

    // Search by Country
    @GetMapping("/search/country")
    public List<PatientResponseDTO> searchByCountry(@RequestParam String country) {
        return patientService.searchByCountry(country);
    }

    // Search by Pincode
    @GetMapping("/search/pincode")
    public List<PatientResponseDTO> searchByPincode(@RequestParam String pincode) {
        return patientService.searchByPincode(pincode);
    }
}
