package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.AmbulanceBooking;
import com.arogyamed.healthcare.model.BookingStatus;
import com.arogyamed.healthcare.model.BookingType;
import com.arogyamed.healthcare.model.EmergencyLevel;
import com.arogyamed.healthcare.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AmbulanceBookingRepository extends JpaRepository<AmbulanceBooking, Long> {

    // =========================
    // PATIENT SEARCH
    // =========================

    List<AmbulanceBooking> findByPatient_Id(Long patientId);

    List<AmbulanceBooking> findByPatient_User_FullNameContainingIgnoreCase(String patientName);

    // =========================
    // AMBULANCE / DRIVER / VEHICLE SEARCH
    // =========================

    List<AmbulanceBooking> findByAmbulance_Id(Long ambulanceId);

    List<AmbulanceBooking> findByAmbulance_AmbulanceNumberContainingIgnoreCase(String vehicleNumber);

    List<AmbulanceBooking> findByAmbulance_DriverNameContainingIgnoreCase(String driverName);

    List<AmbulanceBooking> findByAmbulance_DriverPhone(String driverPhone);

    // =========================
    // HOSPITAL / LOCATION SEARCH
    // =========================

    List<AmbulanceBooking> findByHospitalNameContainingIgnoreCase(String hospitalName);

    List<AmbulanceBooking> findByPickupLocationContainingIgnoreCase(String pickupLocation);

    List<AmbulanceBooking> findByDestinationContainingIgnoreCase(String destination);

    // =========================
    // STATUS / TYPE SEARCH
    // =========================

    List<AmbulanceBooking> findByStatus(BookingStatus status);

    List<AmbulanceBooking> findByBookingType(BookingType bookingType);

    // =========================
    // EMERGENCY LEVEL SEARCH
    // =========================

    List<AmbulanceBooking> findByEmergencyLevel(EmergencyLevel emergencyLevel);

    List<AmbulanceBooking> findByStatusAndEmergencyLevel(BookingStatus status, EmergencyLevel emergencyLevel);

    // =========================
    // PAYMENT STATUS SEARCH
    // =========================

    List<AmbulanceBooking> findByPaymentStatus(PaymentStatus paymentStatus);

    // =========================
    // ETA SEARCH
    // =========================

    List<AmbulanceBooking> findByEtaMinutesLessThanEqual(Integer maxEta);

    List<AmbulanceBooking> findByEtaMinutesBetween(Integer minEta, Integer maxEta);

    // =========================
    // DATE RANGE SEARCH
    // =========================

    List<AmbulanceBooking> findByBookedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // =========================
    // COMBINED SEARCH
    // =========================

    List<AmbulanceBooking> findByPatient_IdAndStatus(Long patientId, BookingStatus status);

    // =========================
    // DASHBOARD
    // =========================

    long countByStatus(BookingStatus status);

    long countByEmergencyLevel(EmergencyLevel emergencyLevel);

    // =========================
    // DYNAMIC COMBINED FILTER
    // =========================

    @Query("""
            SELECT b FROM AmbulanceBooking b
            WHERE (:status IS NULL OR b.status = :status)
            AND (:emergencyLevel IS NULL OR b.emergencyLevel = :emergencyLevel)
            AND (:paymentStatus IS NULL OR b.paymentStatus = :paymentStatus)
            AND (:bookingType IS NULL OR b.bookingType = :bookingType)
            AND (:patientId IS NULL OR b.patient.id = :patientId)
            AND (:ambulanceId IS NULL OR b.ambulance.id = :ambulanceId)
            AND (:startDate IS NULL OR b.bookedAt >= :startDate)
            AND (:endDate IS NULL OR b.bookedAt <= :endDate)
            """)
    List<AmbulanceBooking> searchBookings(
            @Param("status") BookingStatus status,
            @Param("emergencyLevel") EmergencyLevel emergencyLevel,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("bookingType") BookingType bookingType,
            @Param("patientId") Long patientId,
            @Param("ambulanceId") Long ambulanceId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}