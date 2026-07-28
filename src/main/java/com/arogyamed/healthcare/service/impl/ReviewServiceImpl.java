package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.ReviewRequestDTO;
import com.arogyamed.healthcare.dto.ReviewResponseDTO;
import com.arogyamed.healthcare.model.*;
import com.arogyamed.healthcare.repository.*;
import com.arogyamed.healthcare.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO request) {

        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        Review review = new Review();

        review.setPatient(patient);
        review.setReviewType(request.getReviewType());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setReviewDate(LocalDateTime.now());

        switch (request.getReviewType()) {

            case DOCTOR ->
                    review.setDoctor(doctorRepository.findById(request.getDoctorId()).orElseThrow(() ->
                                            new RuntimeException("Doctor not found")));

            case PHARMACIST ->
                    review.setPharmacist(pharmacistRepository.findById(request.getPharmacistId()).orElseThrow(() ->
                                            new RuntimeException("Pharmacist not found")));

            case AMBULANCE ->
                    review.setAmbulance(ambulanceRepository.findById(request.getAmbulanceId()).orElseThrow(() ->
                                            new RuntimeException("Ambulance not found")));

            case DELIVERY_PARTNER ->
                    review.setDeliveryPartner(deliveryPartnerRepository.findById(request.getDeliveryPartnerId()).orElseThrow(() ->
                                            new RuntimeException("Delivery Partner not found")));

            case MEDICINE ->
                    review.setMedicine(medicineRepository.findById(request.getMedicineId()).orElseThrow(() ->
                                            new RuntimeException("Medicine not found")));
        }

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {

        return mapToDTO(reviewRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Review not found")));
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO request) {

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Review not found"));

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {

        return reviewRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByPatient(Long patientId) {

        return reviewRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByDoctor(Long doctorId) {

        return reviewRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByPharmacist(Long pharmacistId) {

        return reviewRepository.findByPharmacistId(pharmacistId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByAmbulance(Long ambulanceId) {

        return reviewRepository.findByAmbulanceId(ambulanceId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByDeliveryPartner(Long deliveryPartnerId) {

        return reviewRepository.findByDeliveryPartnerId(deliveryPartnerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByMedicine(Long medicineId) {

        return reviewRepository.findByMedicineId(medicineId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewRepository.delete(review);
    }

    private ReviewResponseDTO mapToDTO(Review review) {

        ReviewResponseDTO dto = new ReviewResponseDTO();

        dto.setId(review.getId());
        dto.setPatientId(review.getPatient().getId());
        dto.setPatientName(review.getPatient().getUser().getFullName());
        dto.setReviewType(review.getReviewType());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewDate(review.getReviewDate());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<ReviewResponseDTO> searchByReviewType(ReviewType reviewType) {

        return mapToDTOList(reviewRepository.findByReviewType(reviewType));
    }

    @Override
    public List<ReviewResponseDTO> searchByRating(Integer rating) {

        return mapToDTOList(reviewRepository.findByRating(rating));
    }

    @Override
    public List<ReviewResponseDTO> searchByMinimumRating(Integer rating) {

        return mapToDTOList(reviewRepository.findByRatingGreaterThanEqual(rating));
    }

    @Override
    public List<ReviewResponseDTO> searchByReviewDate(LocalDateTime startDate, LocalDateTime endDate) {

        return mapToDTOList(reviewRepository.findByReviewDateBetween(startDate, endDate));
    }

    private List<ReviewResponseDTO> mapToDTOList(List<Review> reviews) {

        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
