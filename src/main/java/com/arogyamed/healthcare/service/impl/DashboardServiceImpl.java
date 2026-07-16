package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.DashboardResponseDTO;
import com.arogyamed.healthcare.model.*;
import com.arogyamed.healthcare.repository.*;
import com.arogyamed.healthcare.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PharmacistRepository pharmacistRepository;
    private final CompanyRepository companyRepository;
    private final WholesalerRepository wholesalerRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final AdminRepository adminRepository;

    private final MedicineRepository medicineRepository;
    private final InventoryRepository inventoryRepository;

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    private final SOSRepository sosRepository;
    private final AmbulanceRepository ambulanceRepository;

    private final ReviewRepository reviewRepository;
    private final QualityCheckRepository qualityCheckRepository;

    private final DeliveryTrackingRepository deliveryTrackingRepository;

    @Override
    public DashboardResponseDTO getAdminDashboard() {

        DashboardResponseDTO dto = new DashboardResponseDTO();

        // =========================
        // USER ANALYTICS
        // =========================

        dto.setTotalUsers(userRepository.count());
        dto.setTotalPatients(patientRepository.count());
        dto.setTotalDoctors(doctorRepository.count());
        dto.setTotalPharmacists(pharmacistRepository.count());
        dto.setTotalCompanies(companyRepository.count());
        dto.setTotalWholesalers(wholesalerRepository.count());
        dto.setTotalDeliveryPartners(deliveryPartnerRepository.count());
        dto.setTotalAdmins(adminRepository.count());

        // =========================
        // MEDICINE ANALYTICS
        // =========================

        dto.setTotalMedicines(medicineRepository.count());
        dto.setTotalInventory(inventoryRepository.count());

        // Available Medicines
        dto.setAvailableMedicines(inventoryRepository.countByQuantityAvailableGreaterThan(0));

        dto.setLowStockMedicines(inventoryRepository.countByQuantityAvailableBetween(1, 10));

        dto.setOutOfStockMedicines(inventoryRepository.countByQuantityAvailable(0));

        // =========================
        // ORDER ANALYTICS
        // =========================

        dto.setTotalOrders(orderRepository.count());

        dto.setPendingOrders(orderRepository.countByStatus(OrderStatus.PENDING));

        dto.setConfirmedOrders(orderRepository.countByStatus(OrderStatus.CONFIRMED));

        dto.setDeliveredOrders(orderRepository.countByStatus(OrderStatus.DELIVERED));

        dto.setCancelledOrders(orderRepository.countByStatus(OrderStatus.CANCELLED));

        // =========================
        // PAYMENT ANALYTICS
        // =========================

        dto.setTotalPayments(paymentRepository.count());

        dto.setSuccessfulPayments(paymentRepository.countByPaymentStatus(PaymentStatus.SUCCESS));

        dto.setPendingPayments(paymentRepository.countByPaymentStatus(PaymentStatus.PENDING));

        dto.setFailedPayments(paymentRepository.countByPaymentStatus(PaymentStatus.FAILED));

        // Revenue
        Double revenue = paymentRepository.getTotalRevenue();

        dto.setTotalRevenue(revenue == null ? 0 : revenue);

        // =========================
        // APPOINTMENTS
        // =========================

        dto.setTotalAppointments(appointmentRepository.count());

        dto.setCompletedAppointments(appointmentRepository.countByStatus(AppointmentStatus.COMPLETED));

        dto.setCancelledAppointments(appointmentRepository.countByStatus(AppointmentStatus.CANCELLED));

        dto.setTotalPrescriptions(prescriptionRepository.count());

        dto.setTotalMedicalRecords(medicalRecordRepository.count());

        // =========================
        // DELIVERY
        // =========================

        dto.setTotalDeliveries(deliveryTrackingRepository.count());

        dto.setCompletedDeliveries(deliveryTrackingRepository.countByStatus(DeliveryStatus.DELIVERED));

        dto.setInTransitDeliveries(deliveryTrackingRepository.countByStatus(DeliveryStatus.OUT_FOR_DELIVERY));

        // =========================
        // SOS
        // =========================

        dto.setTotalSOSRequests(sosRepository.count());

        dto.setTotalAmbulances(ambulanceRepository.count());

        // =========================
        // REVIEW
        // =========================

        dto.setTotalReviews(reviewRepository.count());

        Double rating = reviewRepository.getAverageRating();

        dto.setAverageRating(rating == null ? 0 : rating);

        // =========================
        // QUALITY
        // =========================

        dto.setTotalQualityChecks(qualityCheckRepository.count());

        dto.setApprovedQualityChecks(qualityCheckRepository.countByQualityStatus(QualityStatus.APPROVED));

        dto.setRejectedQualityChecks(qualityCheckRepository.countByQualityStatus(QualityStatus.REJECTED));

        dto.setPendingQualityChecks(qualityCheckRepository.countByQualityStatus(QualityStatus.PENDING));

        // =========================
        // KPIs
        // =========================

        if(dto.getTotalQualityChecks() > 0){

            dto.setMedicineApprovalRate((double) dto.getApprovedQualityChecks() *100 /dto.getTotalQualityChecks());
        }

        if(dto.getTotalDeliveries()>0){

            dto.setDeliverySuccessRate((double) dto.getCompletedDeliveries() *100 /dto.getTotalDeliveries());
        }

        if(dto.getTotalAppointments()>0){

            dto.setAppointmentCompletionRate((double) dto.getCompletedAppointments() *100 /dto.getTotalAppointments());
        }

        return dto;
    }

    @Override
    public DashboardResponseDTO getDoctorDashboard(Long doctorId) {
        return getAdminDashboard();
    }

    @Override
    public DashboardResponseDTO getPatientDashboard(Long patientId) {
        return getAdminDashboard();
    }

    @Override
    public DashboardResponseDTO getCompanyDashboard(Long companyId) {
        return getAdminDashboard();
    }

    @Override
    public DashboardResponseDTO getWholesalerDashboard(Long wholesalerId) {
        return getAdminDashboard();
    }

    @Override
    public DashboardResponseDTO getPharmacistDashboard(Long pharmacistId) {
        return getAdminDashboard();
    }

    @Override
    public DashboardResponseDTO getDeliveryPartnerDashboard(Long deliveryPartnerId) {
        return getAdminDashboard();
    }

    @Override
    public DashboardResponseDTO getAmbulanceDashboard(Long ambulanceId) {
        return getAdminDashboard();
    }


}
