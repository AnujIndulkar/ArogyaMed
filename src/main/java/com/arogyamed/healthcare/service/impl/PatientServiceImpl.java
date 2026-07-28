package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.PatientRequestDTO;
import com.arogyamed.healthcare.dto.PatientResponseDTO;
import com.arogyamed.healthcare.model.Patient;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.PatientRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                        new RuntimeException("User not found"));

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setHeight(request.getHeight());
        patient.setWeight(request.getWeight());
        patient.setAllergies(request.getAllergies());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setEmergencyContactName(request.getEmergencyContactName());
        patient.setEmergencyContactNumber(request.getEmergencyContactNumber());
        patient.setOccupation(request.getOccupation());
        patient.setMaritalStatus(request.getMaritalStatus());
        patient.setProfileImage(request.getProfileImage());
        patient.setInsuranceProvider(request.getInsuranceProvider());
        patient.setInsurancePolicyNumber(request.getInsurancePolicyNumber());
        patient.setCity(request.getCity());
        patient.setDistrict(request.getDistrict());
        patient.setState(request.getState());
        patient.setCountry(request.getCountry());
        patient.setPincode(request.getPincode());

        return mapToDTO(patientRepository.save(patient));
    }

    @Override
    public PatientResponseDTO getPatientByUserId(Long userId) {

        Patient patient = patientRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        return mapToDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(Long userId, PatientRequestDTO request) {

        Patient patient = patientRepository.findByUserId(userId).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setHeight(request.getHeight());
        patient.setWeight(request.getWeight());
        patient.setAllergies(request.getAllergies());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setEmergencyContactName(request.getEmergencyContactName());
        patient.setEmergencyContactNumber(request.getEmergencyContactNumber());
        patient.setOccupation(request.getOccupation());
        patient.setMaritalStatus(request.getMaritalStatus());
        patient.setProfileImage(request.getProfileImage());
        patient.setInsuranceProvider(request.getInsuranceProvider());
        patient.setInsurancePolicyNumber(request.getInsurancePolicyNumber());
        patient.setCity(request.getCity());
        patient.setDistrict(request.getDistrict());
        patient.setState(request.getState());
        patient.setCountry(request.getCountry());
        patient.setPincode(request.getPincode());

        return mapToDTO(patientRepository.save(patient));
    }

    private PatientResponseDTO mapToDTO(Patient patient) {

        PatientResponseDTO dto = new PatientResponseDTO();

        dto.setId(patient.getId());
        dto.setUserId(patient.getUser().getId());
        dto.setFullName(patient.getUser().getFullName());
        dto.setEmail(patient.getUser().getEmail());

        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setBloodGroup(patient.getBloodGroup());
        dto.setHeight(patient.getHeight());
        dto.setWeight(patient.getWeight());
        dto.setAllergies(patient.getAllergies());
        dto.setMedicalHistory(patient.getMedicalHistory());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setEmergencyContactName(patient.getEmergencyContactName());
        dto.setEmergencyContactNumber(patient.getEmergencyContactNumber());
        dto.setOccupation(patient.getOccupation());
        dto.setMaritalStatus(patient.getMaritalStatus());
        dto.setProfileImage(patient.getProfileImage());
        dto.setInsuranceProvider(patient.getInsuranceProvider());
        dto.setInsurancePolicyNumber(patient.getInsurancePolicyNumber());
        dto.setCity(patient.getCity());
        dto.setDistrict(patient.getDistrict());
        dto.setState(patient.getState());
        dto.setCountry(patient.getCountry());
        dto.setPincode(patient.getPincode());

        return dto;
    }

    @Override
    public List<PatientResponseDTO> searchByName(String name) {

        return patientRepository.findByUserFullNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByEmail(String email) {

        return patientRepository.findByUserEmailContainingIgnoreCase(email)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByPhone(String phoneNumber) {

        return patientRepository.findByUserPhoneNumberContainingIgnoreCase(phoneNumber)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByGender(String gender) {

        return patientRepository.findByGenderContainingIgnoreCase(gender)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByBloodGroup(String bloodGroup) {

        return patientRepository.findByBloodGroupContainingIgnoreCase(bloodGroup)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByAge(Integer age) {

        return patientRepository.findByAge(age)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByDateOfBirth(LocalDate dateOfBirth) {

        return patientRepository.findByDateOfBirth(dateOfBirth)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByAllergies(String allergies) {

        return patientRepository.findByAllergiesContainingIgnoreCase(allergies)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByMedicalHistory(String medicalHistory) {

        return patientRepository.findByMedicalHistoryContainingIgnoreCase(medicalHistory)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByEmergencyContactName(String emergencyContactName) {

        return patientRepository.findByEmergencyContactNameContainingIgnoreCase(emergencyContactName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByEmergencyContactNumber(String emergencyContactNumber) {

        return patientRepository.findByEmergencyContactNumberContainingIgnoreCase(emergencyContactNumber)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByInsuranceProvider(String insuranceProvider) {

        return patientRepository.findByInsuranceProviderContainingIgnoreCase(insuranceProvider)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByInsurancePolicyNumber(String insurancePolicyNumber) {

        return patientRepository.findByInsurancePolicyNumberContainingIgnoreCase(insurancePolicyNumber)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByOccupation(String occupation) {

        return patientRepository.findByOccupationContainingIgnoreCase(occupation)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByMaritalStatus(String maritalStatus) {

        return patientRepository.findByMaritalStatusContainingIgnoreCase(maritalStatus)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByCity(String city) {

        return patientRepository.findByCityContainingIgnoreCase(city)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByDistrict(String district) {

        return patientRepository.findByDistrictContainingIgnoreCase(district)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByState(String state) {

        return patientRepository.findByStateContainingIgnoreCase(state)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByCountry(String country) {

        return patientRepository.findByCountryContainingIgnoreCase(country)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<PatientResponseDTO> searchByPincode(String pincode) {

        return patientRepository.findByPincodeContainingIgnoreCase(pincode)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
}
