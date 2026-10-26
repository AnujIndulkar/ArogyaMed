package com.arogyamed.service.impl;

import com.arogyamed.dto.InspectionDTO;
import com.arogyamed.dto.QualityCheckResponseDTO;
import com.arogyamed.model.Medicine;
import com.arogyamed.model.QualityCheck;
import com.arogyamed.model.QualityInspector;
import com.arogyamed.model.QualityStatus;
import com.arogyamed.repository.MedicineRepository;
import com.arogyamed.repository.QualityCheckRepository;
import com.arogyamed.repository.QualityInspectorRepository;
import com.arogyamed.service.InspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

    private final MedicineRepository medicineRepository;
    private final QualityCheckRepository qualityCheckRepository;
    private final QualityInspectorRepository qualityInspectorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InspectionDTO.PendingBatch> pendingBatches(String search, int limit) {
        int size = Math.min(Math.max(limit, 1), 100);
        String term = search == null ? "" : search.trim();

        return medicineRepository.findPendingInspection(term, PageRequest.of(0, size))
                .stream()
                .map(m -> new InspectionDTO.PendingBatch(
                        m.getId(),
                        m.getMedicineName(),
                        m.getCompany().getCompanyName(),
                        m.getBatchNumber(),
                        m.getManufacturingDate(),
                        m.getExpiryDate()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QualityCheckResponseDTO> myInspections(String inspectorEmail) {
        QualityInspector inspector = findInspector(inspectorEmail);

        return qualityCheckRepository
                .findByInspector_IdOrderByInspectionDateDescIdDesc(inspector.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InspectionDTO.Summary summary(String inspectorEmail) {
        QualityInspector inspector = findInspector(inspectorEmail);

        long pending = medicineRepository.findPendingInspection("", PageRequest.of(0, 1)).getTotalElements();

        return new InspectionDTO.Summary(
                pending,
                qualityCheckRepository.countByInspector_IdAndQualityStatus(inspector.getId(), QualityStatus.APPROVED),
                qualityCheckRepository.countByInspector_IdAndQualityStatus(inspector.getId(), QualityStatus.REJECTED),
                qualityCheckRepository.countByInspector_Id(inspector.getId()));
    }

    @Override
    @Transactional
    public QualityCheckResponseDTO submit(String inspectorEmail, InspectionDTO.SubmitRequest request) {
        QualityInspector inspector = findInspector(inspectorEmail);

        Medicine medicine = medicineRepository.findById(request.medicineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found"));

        if (medicine.getCompany() == null || medicine.getBatchNumber() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This medicine has no company or batch number, so it cannot be inspected");
        }

        if (qualityCheckRepository.existsByMedicine_IdAndBatchNumber(medicine.getId(), medicine.getBatchNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This batch has already been inspected");
        }

        boolean allChecksPassed = request.packagingVerified() && request.sealVerified()
                && request.temperatureVerified() && request.expiryVerified();

        // the rules live here on the server, not only in the form
        if (request.approve() && !allChecksPassed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A batch can only be approved when all four checks are ticked");
        }
        if (!request.approve() && (request.remarks() == null || request.remarks().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Please write a reason for rejecting this batch");
        }

        QualityCheck saved = qualityCheckRepository.save(QualityCheck.builder()
                .medicine(medicine)
                .company(medicine.getCompany())
                .inspector(inspector)
                .batchNumber(medicine.getBatchNumber())
                .packagingVerified(request.packagingVerified())
                .sealVerified(request.sealVerified())
                .temperatureVerified(request.temperatureVerified())
                .expiryVerified(request.expiryVerified())
                .inspectorRemarks(request.remarks() == null ? null : request.remarks().trim())
                .inspectionDate(LocalDate.now())
                .qualityStatus(request.approve() ? QualityStatus.APPROVED : QualityStatus.REJECTED)
                .build());

        return toResponse(saved);
    }

    private QualityInspector findInspector(String email) {
        return qualityInspectorRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Your inspector profile is not set up yet"));
    }

    private QualityCheckResponseDTO toResponse(QualityCheck qc) {
        return QualityCheckResponseDTO.builder()
                .id(qc.getId())
                .medicineId(qc.getMedicine().getId())
                .medicineName(qc.getMedicine().getMedicineName())
                .companyId(qc.getCompany().getId())
                .companyName(qc.getCompany().getCompanyName())
                .inspectorId(qc.getInspector() != null ? qc.getInspector().getId() : null)
                .inspectorName(qc.getInspector() != null ? qc.getInspector().getUser().getFullName() : null)
                .batchNumber(qc.getBatchNumber())
                .packagingVerified(qc.isPackagingVerified())
                .sealVerified(qc.isSealVerified())
                .temperatureVerified(qc.isTemperatureVerified())
                .expiryVerified(qc.isExpiryVerified())
                .inspectorRemarks(qc.getInspectorRemarks())
                .inspectionDate(qc.getInspectionDate())
                .qualityStatus(qc.getQualityStatus())
                .build();
    }
}