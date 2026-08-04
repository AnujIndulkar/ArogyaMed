package com.arogyamed.repository;

import com.arogyamed.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    // ================= Search =================

    // Search by Patient Name
    List<MedicalRecord> findByPatient_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Diagnosis
    List<MedicalRecord> findByDiagnosisContainingIgnoreCase(String diagnosis);

    // Search by Treatment
    List<MedicalRecord> findByTreatmentContainingIgnoreCase(String treatment);

    // Search by Doctor Notes
    List<MedicalRecord> findByDoctorNotesContainingIgnoreCase(String doctorNotes);

    // Search by Visit Date
    List<MedicalRecord> findByVisitDate(LocalDate visitDate);

    // Search by Visit Date Range
    List<MedicalRecord> findByVisitDateBetween(LocalDate startDate, LocalDate endDate);
}