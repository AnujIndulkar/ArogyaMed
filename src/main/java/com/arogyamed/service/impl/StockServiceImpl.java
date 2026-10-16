package com.arogyamed.service.impl;

import com.arogyamed.dto.StockItemDTO;
import com.arogyamed.model.Medicine;
import com.arogyamed.repository.MedicineRepository;
import com.arogyamed.service.StockService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private static final int LOW_STOCK_LIMIT = 10;
    private static final int EXPIRING_SOON_DAYS = 90;

    private final MedicineRepository medicineRepository;

    public StockServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockItemDTO> listStock() {

        List<StockItemDTO> result = new ArrayList<>();
        for (Medicine medicine : medicineRepository.findAll(Sort.by("medicineName"))) {
            result.add(toDto(medicine));
        }
        return result;
    }

    @Override
    @Transactional
    public StockItemDTO restock(Long medicineId, int quantity) {

        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found"));

        int current = medicine.getStockQuantity() == null ? 0 : medicine.getStockQuantity();
        medicine.setStockQuantity(current + quantity);

        return toDto(medicineRepository.save(medicine));
    }

    private StockItemDTO toDto(Medicine medicine) {

        int stock = medicine.getStockQuantity() == null ? 0 : medicine.getStockQuantity();

        StockItemDTO dto = new StockItemDTO();
        dto.setMedicineId(medicine.getId());
        dto.setMedicineName(medicine.getMedicineName());
        dto.setCategory(medicine.getCategory());
        dto.setBatchNumber(medicine.getBatchNumber());
        dto.setPrice(medicine.getPrice());
        dto.setStockQuantity(stock);
        dto.setExpiryDate(medicine.getExpiryDate());

        if (medicine.getCompany() != null) {
            dto.setCompanyName(medicine.getCompany().getCompanyName());
        }

        if (stock == 0) {
            dto.setStockStatus("OUT_OF_STOCK");
        } else if (stock <= LOW_STOCK_LIMIT) {
            dto.setStockStatus("LOW_STOCK");
        } else {
            dto.setStockStatus("IN_STOCK");
        }

        LocalDate expiry = medicine.getExpiryDate();
        LocalDate today = LocalDate.now();
        if (expiry == null) {
            dto.setExpiryStatus("OK");
        } else if (expiry.isBefore(today)) {
            dto.setExpiryStatus("EXPIRED");
        } else if (!expiry.isAfter(today.plusDays(EXPIRING_SOON_DAYS))) {
            dto.setExpiryStatus("EXPIRING_SOON");
        } else {
            dto.setExpiryStatus("OK");
        }

        return dto;
    }
}
