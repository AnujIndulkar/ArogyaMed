package com.arogyamed.repository;

import com.arogyamed.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.arogyamed.model.ReviewType;
import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPatientId(Long patientId);

    List<Review> findByDoctorId(Long doctorId);

    List<Review> findByPharmacistId(Long pharmacistId);

    List<Review> findByAmbulanceId(Long ambulanceId);

    List<Review> findByDeliveryPartnerId(Long deliveryPartnerId);

    List<Review> findByMedicineId(Long medicineId);

    @Query("SELECT COALESCE(AVG(r.rating),0) FROM Review r")
    Double getAverageRating();

    // ================= Search =================

    // Search by Review Type
    List<Review> findByReviewType(ReviewType reviewType);

    // Search by Rating
    List<Review> findByRating(Integer rating);

    // Search by Minimum Rating
    List<Review> findByRatingGreaterThanEqual(Integer rating);

    // Search by Review Date Range
    List<Review> findByReviewDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
