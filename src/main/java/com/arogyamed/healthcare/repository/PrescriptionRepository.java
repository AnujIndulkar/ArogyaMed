package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // ================= Search =================

    // Search by Patient Name
    List<Prescription> findByPatient_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Doctor Name
    List<Prescription> findByDoctor_User_FullNameContainingIgnoreCase(String fullName);

    // Search by Diagnosis
    List<Prescription> findByDiagnosisContainingIgnoreCase(String diagnosis);

    // Search by Medicine
    List<Prescription> findByMedicinesContainingIgnoreCase(String medicine);

    // Search by Prescription Date
    List<Prescription> findByPrescriptionDate(LocalDate prescriptionDate);

    // Search by Date Range
    List<Prescription> findByPrescriptionDateBetween(LocalDate startDate,
                                                     LocalDate endDate);

    // Search by Notes
    List<Prescription> findByNotesContainingIgnoreCase(String notes);

    List<Prescription> findByPatient_Id(Long patientId);
}
