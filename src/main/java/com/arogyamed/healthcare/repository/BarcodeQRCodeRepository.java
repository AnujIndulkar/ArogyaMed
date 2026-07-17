package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.BarcodeQRCode;
import com.arogyamed.healthcare.model.BarcodeType;
import com.arogyamed.healthcare.model.Medicine;
import com.arogyamed.healthcare.model.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BarcodeQRCodeRepository extends JpaRepository<BarcodeQRCode, Long> {

    Optional<BarcodeQRCode> findByBarcode(String barcode);

    Optional<BarcodeQRCode> findByQrCode(String qrCode);

    Optional<BarcodeQRCode> findByMedicine(Medicine medicine);

    List<BarcodeQRCode> findByBarcodeType(BarcodeType barcodeType);

    List<BarcodeQRCode> findByVerificationStatus(VerificationStatus verificationStatus);

    long countByVerificationStatus(VerificationStatus verificationStatus);

    long countByBarcodeType(BarcodeType barcodeType);

}