package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.MedicineRequestDTO;
import com.arogyamed.healthcare.dto.MedicineResponseDTO;
import com.arogyamed.healthcare.model.Company;
import com.arogyamed.healthcare.model.Medicine;
import com.arogyamed.healthcare.repository.CompanyRepository;
import com.arogyamed.healthcare.repository.MedicineRepository;
import com.arogyamed.healthcare.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Value("${media.upload.path}")
    private String mediaUploadPath;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public MedicineResponseDTO createMedicine(MedicineRequestDTO request) {

        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(() ->
                        new RuntimeException("Company not found"));

        Medicine medicine = new Medicine();

        medicine.setCompany(company);
        medicine.setMedicineName(request.getMedicineName());
        medicine.setCategory(request.getCategory());
        medicine.setDescription(request.getDescription());
        medicine.setPrice(request.getPrice());
        medicine.setBatchNumber(request.getBatchNumber());
        medicine.setManufacturingDate(request.getManufacturingDate());
        medicine.setExpiryDate(request.getExpiryDate());
        medicine.setStockQuantity(request.getStockQuantity());

        return mapToDTO(medicineRepository.save(medicine));
    }

    @Override
    public MedicineResponseDTO getMedicineById(Long id) {

        Medicine medicine = medicineRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        return mapToDTO(medicine);
    }

    @Override
    public MedicineResponseDTO updateMedicine(Long id, MedicineRequestDTO request) {

        Medicine medicine = medicineRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        medicine.setMedicineName(request.getMedicineName());
        medicine.setCategory(request.getCategory());
        medicine.setDescription(request.getDescription());
        medicine.setPrice(request.getPrice());
        medicine.setBatchNumber(request.getBatchNumber());
        medicine.setManufacturingDate(request.getManufacturingDate());
        medicine.setExpiryDate(request.getExpiryDate());
        medicine.setStockQuantity(request.getStockQuantity());

        return mapToDTO(medicineRepository.save(medicine));
    }

    @Override
    public List<MedicineResponseDTO> getAllMedicines() {

        return medicineRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private MedicineResponseDTO mapToDTO(Medicine medicine) {

        MedicineResponseDTO dto = new MedicineResponseDTO();

        dto.setId(medicine.getId());
        dto.setCompanyId(medicine.getCompany().getId());
        dto.setCompanyName(medicine.getCompany().getCompanyName());
        dto.setMedicineName(medicine.getMedicineName());
        dto.setCategory(medicine.getCategory());
        dto.setDescription(medicine.getDescription());
        dto.setPrice(medicine.getPrice());
        dto.setBatchNumber(medicine.getBatchNumber());
        dto.setManufacturingDate(medicine.getManufacturingDate());
        dto.setExpiryDate(medicine.getExpiryDate());
        dto.setStockQuantity(medicine.getStockQuantity());

        return dto;
    }

    @Override
    public List<MedicineResponseDTO> searchByMedicineName(String medicineName) {

        return mapToDTOList(medicineRepository.findByMedicineNameContainingIgnoreCase(medicineName));
    }

    @Override
    public List<MedicineResponseDTO> searchByCategory(String category) {

        return mapToDTOList(medicineRepository.findByCategoryContainingIgnoreCase(category));
    }

    @Override
    public List<MedicineResponseDTO> searchByCompany(String companyName) {

        return mapToDTOList(medicineRepository.findByCompany_CompanyNameContainingIgnoreCase(companyName));
    }

    @Override
    public List<MedicineResponseDTO> searchByBatchNumber(String batchNumber) {

        return mapToDTOList(medicineRepository.findByBatchNumberContainingIgnoreCase(batchNumber));
    }

    @Override
    public List<MedicineResponseDTO> searchByPriceRange(Double minPrice, Double maxPrice) {

        return mapToDTOList(medicineRepository.findByPriceBetween(minPrice, maxPrice));
    }

    @Override
    public List<MedicineResponseDTO> searchByExpiryDate(LocalDate expiryDate) {

        return mapToDTOList(medicineRepository.findByExpiryDateBefore(expiryDate));
    }

    @Override
    public List<MedicineResponseDTO> searchLowStockMedicines(Integer stockQuantity) {

        return mapToDTOList(medicineRepository.findByStockQuantityLessThanEqual(stockQuantity));
    }

    private List<MedicineResponseDTO> mapToDTOList(List<Medicine> medicines) {
        return medicines.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicineResponseDTO uploadMedicineImage(Long id, MultipartFile file) {

        Medicine medicine = medicineRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Medicine not found"));

        try {

            String originalFileName = file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String storedFileName = UUID.randomUUID() + extension;

            Path directoryPath = Paths.get(mediaUploadPath, "medicine-images");

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path targetPath = directoryPath.resolve(storedFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            medicine.setImageUrl("/files/medicine-images/" + storedFileName);

            Medicine updatedMedicine = medicineRepository.save(medicine);

            return mapToDTO(updatedMedicine);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload medicine image.", e);
        }
    }
}
