package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.ReviewRequestDTO;
import com.arogyamed.healthcare.dto.ReviewResponseDTO;
import com.arogyamed.healthcare.model.ReviewType;

import java.time.LocalDateTime;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewRequestDTO request);

    ReviewResponseDTO getReviewById(Long id);

    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO request);

    List<ReviewResponseDTO> getAllReviews();

    List<ReviewResponseDTO> getReviewsByPatient(Long patientId);

    List<ReviewResponseDTO> getReviewsByDoctor(Long doctorId);

    List<ReviewResponseDTO> getReviewsByPharmacist(Long pharmacistId);

    List<ReviewResponseDTO> getReviewsByAmbulance(Long ambulanceId);

    List<ReviewResponseDTO> getReviewsByDeliveryPartner(Long deliveryPartnerId);

    List<ReviewResponseDTO> getReviewsByMedicine(Long medicineId);

    void deleteReview(Long id);

    // ================= Search =================

    // Search by Review Type
    List<ReviewResponseDTO> searchByReviewType(ReviewType reviewType);

    // Search by Rating
    List<ReviewResponseDTO> searchByRating(Integer rating);

    // Search by Minimum Rating
    List<ReviewResponseDTO> searchByMinimumRating(Integer rating);

    // Search by Review Date Range
    List<ReviewResponseDTO> searchByReviewDate(LocalDateTime startDate, LocalDateTime endDate);

}
