package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.ReviewRequestDTO;
import com.arogyamed.healthcare.dto.ReviewResponseDTO;

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

}
