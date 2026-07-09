package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.MedicineRequestDTO;
import com.arogyamed.healthcare.dto.MedicineResponseDTO;

import java.util.List;

public interface MedicineService {

    MedicineResponseDTO createMedicine(MedicineRequestDTO request);

    MedicineResponseDTO getMedicineById(Long id);

    MedicineResponseDTO updateMedicine(Long id, MedicineRequestDTO request);

    List<MedicineResponseDTO> getAllMedicines();
}
