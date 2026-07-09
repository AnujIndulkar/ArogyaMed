package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPatientId(Long patientId);

    List<Review> findByDoctorId(Long doctorId);

    List<Review> findByPharmacistId(Long pharmacistId);

    List<Review> findByAmbulanceId(Long ambulanceId);

    List<Review> findByDeliveryPartnerId(Long deliveryPartnerId);

    List<Review> findByMedicineId(Long medicineId);

}
