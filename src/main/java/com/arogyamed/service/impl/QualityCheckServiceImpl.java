package com.arogyamed.service.impl;

import com.arogyamed.dto.QualityCheckRequestDTO;
import com.arogyamed.dto.QualityCheckResponseDTO;
import com.arogyamed.model.Admin;
import com.arogyamed.model.Company;
import com.arogyamed.model.Medicine;
import com.arogyamed.model.QualityCheck;
import com.arogyamed.repository.AdminRepository;
import com.arogyamed.repository.CompanyRepository;
import com.arogyamed.repository.MedicineRepository;
import com.arogyamed.repository.QualityCheckRepository;
import com.arogyamed.service.QualityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.arogyamed.model.QualityStatus;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QualityCheckServiceImpl implements QualityCheckService {

    private final QualityCheckRepository qualityCheckRepository;
    private final MedicineRepository medicineRepository;
    private final CompanyRepository companyRepository;
    private final AdminRepository adminRepository;

    @Override
    public QualityCheckResponseDTO createQualityCheck(QualityCheckRequestDTO requestDTO) {

        Medicine medicine = medicineRepository.findById(requestDTO.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        Company company = companyRepository.findById(requestDTO.getCompanyId()).orElseThrow(() ->
                        new RuntimeException("Company not found"));

        Admin admin = adminRepository.findById(requestDTO.getAdminId()).orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        QualityCheck qualityCheck = QualityCheck.builder()
                .medicine(medicine)
                .company(company)
                .inspector(admin)
                .batchNumber(requestDTO.getBatchNumber())
                .packagingVerified(requestDTO.isPackagingVerified())
                .sealVerified(requestDTO.isSealVerified())
                .temperatureVerified(requestDTO.isTemperatureVerified())
                .expiryVerified(requestDTO.isExpiryVerified())
                .inspectorRemarks(requestDTO.getInspectorRemarks())
                .inspectionDate(requestDTO.getInspectionDate() == null
                                ? LocalDate.now()
                                : requestDTO.getInspectionDate())
                .qualityStatus(requestDTO.getQualityStatus()).qualityStatus(requestDTO.getQualityStatus() == null
                                ? QualityStatus.PENDING
                                : requestDTO.getQualityStatus())
                .build();

        return mapToResponseDTO(qualityCheckRepository.save(qualityCheck));
    }

    @Override
    public QualityCheckResponseDTO getQualityCheckById(Long id) {

        QualityCheck qualityCheck = qualityCheckRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Quality Check not found"));

        return mapToResponseDTO(qualityCheck);
    }

    @Override
    public List<QualityCheckResponseDTO> getAllQualityChecks() {

        return qualityCheckRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QualityCheckResponseDTO updateQualityCheck(Long id, QualityCheckRequestDTO requestDTO) {

        QualityCheck qualityCheck = qualityCheckRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Quality Check not found"));

        Medicine medicine = medicineRepository.findById(requestDTO.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        Company company = companyRepository.findById(requestDTO.getCompanyId()).orElseThrow(() ->
                        new RuntimeException("Company not found"));

        Admin admin = adminRepository.findById(requestDTO.getAdminId()).orElseThrow(() ->
                        new RuntimeException("Admin not found"));

        qualityCheck.setMedicine(medicine);
        qualityCheck.setCompany(company);
        qualityCheck.setInspector(admin);
        qualityCheck.setBatchNumber(requestDTO.getBatchNumber());
        qualityCheck.setPackagingVerified(requestDTO.isPackagingVerified());
        qualityCheck.setSealVerified(requestDTO.isSealVerified());
        qualityCheck.setTemperatureVerified(requestDTO.isTemperatureVerified());
        qualityCheck.setExpiryVerified(requestDTO.isExpiryVerified());
        qualityCheck.setInspectorRemarks(requestDTO.getInspectorRemarks());
        qualityCheck.setInspectionDate(requestDTO.getInspectionDate());
        qualityCheck.setQualityStatus(requestDTO.getQualityStatus());

        return mapToResponseDTO(qualityCheckRepository.save(qualityCheck));
    }

    @Override
    public void deleteQualityCheck(Long id) {

        QualityCheck qualityCheck = qualityCheckRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Quality Check not found"));

        qualityCheckRepository.delete(qualityCheck);
    }

    private QualityCheckResponseDTO mapToResponseDTO(QualityCheck qualityCheck) {

        return QualityCheckResponseDTO.builder()
                .id(qualityCheck.getId())
                .medicineId(qualityCheck.getMedicine().getId())
                .medicineName(qualityCheck.getMedicine().getMedicineName())
                .companyId(qualityCheck.getCompany().getId())
                .companyName(qualityCheck.getCompany().getCompanyName())
                .inspectorId(qualityCheck.getInspector().getId())
                .inspectorName(qualityCheck.getInspector().getUser().getFullName())
                .batchNumber(qualityCheck.getBatchNumber())
                .packagingVerified(qualityCheck.isPackagingVerified())
                .sealVerified(qualityCheck.isSealVerified())
                .temperatureVerified(qualityCheck.isTemperatureVerified())
                .expiryVerified(qualityCheck.isExpiryVerified())
                .inspectorRemarks(qualityCheck.getInspectorRemarks())
                .inspectionDate(qualityCheck.getInspectionDate())
                .qualityStatus(qualityCheck.getQualityStatus())
                .build();
    }

    // ================= Search =================

    @Override
    public List<QualityCheckResponseDTO> searchByMedicine(Long medicineId) {

        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        return mapToResponseDTOList(qualityCheckRepository.findByMedicine(medicine));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByCompany(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        return mapToResponseDTOList(qualityCheckRepository.findByCompany(company));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByInspector(Long adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return mapToResponseDTOList(qualityCheckRepository.findByInspector(admin));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByQualityStatus(QualityStatus qualityStatus) {

        return mapToResponseDTOList(qualityCheckRepository.findByQualityStatus(qualityStatus));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByBatchNumber(String batchNumber) {

        return mapToResponseDTOList(qualityCheckRepository.findByBatchNumber(batchNumber));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByInspectionDate(LocalDate inspectionDate) {

        return mapToResponseDTOList(qualityCheckRepository.findByInspectionDate(inspectionDate));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByInspectionDate(LocalDate startDate, LocalDate endDate) {

        return mapToResponseDTOList(qualityCheckRepository.findByInspectionDateBetween(startDate, endDate));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByPackagingVerified(boolean packagingVerified) {

        return mapToResponseDTOList(qualityCheckRepository.findByPackagingVerified(packagingVerified));
    }

    @Override
    public List<QualityCheckResponseDTO> searchBySealVerified(boolean sealVerified) {

        return mapToResponseDTOList(qualityCheckRepository.findBySealVerified(sealVerified));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByTemperatureVerified(boolean temperatureVerified) {

        return mapToResponseDTOList(qualityCheckRepository.findByTemperatureVerified(temperatureVerified));
    }

    @Override
    public List<QualityCheckResponseDTO> searchByExpiryVerified(boolean expiryVerified) {

        return mapToResponseDTOList(qualityCheckRepository.findByExpiryVerified(expiryVerified));
    }

    private List<QualityCheckResponseDTO> mapToResponseDTOList(
            List<QualityCheck> qualityChecks) {

        return qualityChecks.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

}
