package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.ReviewRequestDTO;
import com.arogyamed.healthcare.dto.ReviewResponseDTO;
import com.arogyamed.healthcare.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ReviewResponseDTO createReview(@RequestBody ReviewRequestDTO request) {
        return reviewService.createReview(request);
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO updateReview(@PathVariable Long id, @RequestBody ReviewRequestDTO request) {
        return reviewService.updateReview(id, request);
    }

    @GetMapping
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/patient/{patientId}")
    public List<ReviewResponseDTO> getReviewsByPatient(@PathVariable Long patientId) {
        return reviewService.getReviewsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<ReviewResponseDTO> getReviewsByDoctor(@PathVariable Long doctorId) {
        return reviewService.getReviewsByDoctor(doctorId);
    }

    @GetMapping("/pharmacist/{pharmacistId}")
    public List<ReviewResponseDTO> getReviewsByPharmacist(@PathVariable Long pharmacistId) {
        return reviewService.getReviewsByPharmacist(pharmacistId);
    }

    @GetMapping("/ambulance/{ambulanceId}")
    public List<ReviewResponseDTO> getReviewsByAmbulance(@PathVariable Long ambulanceId) {
        return reviewService.getReviewsByAmbulance(ambulanceId);
    }

    @GetMapping("/delivery-partner/{deliveryPartnerId}")
    public List<ReviewResponseDTO> getReviewsByDeliveryPartner(@PathVariable Long deliveryPartnerId) {
        return reviewService.getReviewsByDeliveryPartner(deliveryPartnerId);
    }

    @GetMapping("/medicine/{medicineId}")
    public List<ReviewResponseDTO> getReviewsByMedicine(@PathVariable Long medicineId) {
        return reviewService.getReviewsByMedicine(medicineId);
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully.";
    }

}
