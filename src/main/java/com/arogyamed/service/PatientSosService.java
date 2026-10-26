package com.arogyamed.service;

import com.arogyamed.dto.PatientSosDTO;

import java.util.Optional;

public interface PatientSosService {

    // the SOS button: creates an emergency for the logged-in patient
    PatientSosDTO.Status create(String email, PatientSosDTO.CreateRequest request);

    // the patient's current emergency, if there is one
    Optional<PatientSosDTO.Status> active(String email);

    // cancel a request that no ambulance has accepted yet
    PatientSosDTO.Status cancel(String email, Long sosId);
}