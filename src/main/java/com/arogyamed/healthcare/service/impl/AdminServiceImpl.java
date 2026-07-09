package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.AdminDashboardDTO;
import com.arogyamed.healthcare.dto.AdminRequestDTO;
import com.arogyamed.healthcare.dto.AdminResponseDTO;
import com.arogyamed.healthcare.exception.ResourceNotFoundException;
import com.arogyamed.healthcare.model.*;
import com.arogyamed.healthcare.repository.*;
import com.arogyamed.healthcare.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PharmacistRepository pharmacistRepository;
    private final CompanyRepository companyRepository;
    private final WholesalerRepository wholesalerRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final AmbulanceRepository ambulanceRepository;

    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final SOSRepository sosRepository;
    private final KYCRepository kycRepository;

    public AdminServiceImpl(
            AdminRepository adminRepository,
            UserRepository userRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            PharmacistRepository pharmacistRepository,
            CompanyRepository companyRepository,
            WholesalerRepository wholesalerRepository,
            DeliveryPartnerRepository deliveryPartnerRepository,
            AmbulanceRepository ambulanceRepository,
            MedicineRepository medicineRepository,
            OrderRepository orderRepository,
            PaymentRepository paymentRepository,
            ReviewRepository reviewRepository,
            SOSRepository sosRepository,
            KYCRepository kycRepository) {

        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.companyRepository = companyRepository;
        this.wholesalerRepository = wholesalerRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.ambulanceRepository = ambulanceRepository;
        this.medicineRepository = medicineRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.reviewRepository = reviewRepository;
        this.sosRepository = sosRepository;
        this.kycRepository = kycRepository;
    }

    private String generateEmployeeId() {

        long count = adminRepository.count() + 1;

        return String.format("ADM%04d", count);
    }

    private AdminResponseDTO mapToResponse(Admin admin) {

        AdminResponseDTO dto = new AdminResponseDTO();

        dto.setId(admin.getId());
        dto.setUserId(admin.getUser().getId());
        dto.setUserName(admin.getUser().getFullName());
        dto.setEmail(admin.getUser().getEmail());

        dto.setEmployeeId(admin.getEmployeeId());
        dto.setAdminType(admin.getAdminType());
        dto.setDepartment(admin.getDepartment());
        dto.setDesignation(admin.getDesignation());
        dto.setOfficeLocation(admin.getOfficeLocation());
        dto.setJoiningDate(admin.getJoiningDate());
        dto.setStatus(admin.getStatus());

        return dto;
    }

    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId()).orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID : " + requestDTO.getUserId()));

        Admin admin = new Admin();

        admin.setUser(user);
        admin.setEmployeeId(generateEmployeeId());
        admin.setAdminType(requestDTO.getAdminType());
        admin.setDepartment(requestDTO.getDepartment());
        admin.setDesignation(requestDTO.getDesignation());
        admin.setOfficeLocation(requestDTO.getOfficeLocation());
        admin.setJoiningDate(requestDTO.getJoiningDate());
        admin.setStatus(requestDTO.getStatus());

        Admin savedAdmin = adminRepository.save(admin);

        return mapToResponse(savedAdmin);
    }

    @Override
    public AdminResponseDTO getAdminById(Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found with ID : " + id));

        return mapToResponse(admin);
    }

    @Override
    public List<AdminResponseDTO> getAllAdmins() {

        List<Admin> admins = adminRepository.findAll();

        List<AdminResponseDTO> responseList = new ArrayList<>();

        for (Admin admin : admins) {
            responseList.add(mapToResponse(admin));
        }

        return responseList;
    }

    @Override
    public AdminResponseDTO updateAdmin(Long id, AdminRequestDTO requestDTO) {

        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found with ID : " + id));

        User user = userRepository.findById(requestDTO.getUserId()).orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID : " + requestDTO.getUserId()));

        admin.setUser(user);
        admin.setAdminType(requestDTO.getAdminType());
        admin.setDepartment(requestDTO.getDepartment());
        admin.setDesignation(requestDTO.getDesignation());
        admin.setOfficeLocation(requestDTO.getOfficeLocation());
        admin.setJoiningDate(requestDTO.getJoiningDate());
        admin.setStatus(requestDTO.getStatus());

        Admin updatedAdmin = adminRepository.save(admin);

        return mapToResponse(updatedAdmin);
    }

    @Override
    public void deleteAdmin(Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found with ID : " + id));

        adminRepository.delete(admin);
    }

    @Override
    public AdminResponseDTO blockAdmin(Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found with ID : " + id));

        admin.setStatus(AdminStatus.INACTIVE);

        Admin updatedAdmin = adminRepository.save(admin);

        return mapToResponse(updatedAdmin);
    }

    @Override
    public AdminResponseDTO unblockAdmin(Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Admin not found with ID : " + id));

        admin.setStatus(AdminStatus.ACTIVE);

        Admin updatedAdmin = adminRepository.save(admin);

        return mapToResponse(updatedAdmin);
    }
    @Override
    public AdminDashboardDTO getDashboard() {

        AdminDashboardDTO dashboard = new AdminDashboardDTO();

        dashboard.setTotalUsers(userRepository.count());
        dashboard.setTotalPatients(patientRepository.count());
        dashboard.setTotalDoctors(doctorRepository.count());
        dashboard.setTotalPharmacists(pharmacistRepository.count());
        dashboard.setTotalCompanies(companyRepository.count());
        dashboard.setTotalWholesalers(wholesalerRepository.count());
        dashboard.setTotalDeliveryPartners(deliveryPartnerRepository.count());
        dashboard.setTotalAmbulances(ambulanceRepository.count());

        dashboard.setTotalMedicines(medicineRepository.count());
        dashboard.setTotalOrders(orderRepository.count());
        dashboard.setTotalPayments(paymentRepository.count());

        dashboard.setTotalReviews(reviewRepository.count());
        dashboard.setTotalSOSRequests(sosRepository.count());

        dashboard.setPendingKyc(0L);
        dashboard.setApprovedKyc(0L);
        dashboard.setRejectedKyc(0L);

        dashboard.setTotalRevenue(0.0);

        return dashboard;
    }
}
