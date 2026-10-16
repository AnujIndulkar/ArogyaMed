package com.arogyamed.service.impl;

import com.arogyamed.dto.CheckoutRequestDTO;
import com.arogyamed.dto.CheckoutResponseDTO;
import com.arogyamed.model.Medicine;
import com.arogyamed.model.Order;
import com.arogyamed.model.OrderItem;
import com.arogyamed.model.OrderStatus;
import com.arogyamed.model.Patient;
import com.arogyamed.model.Payment;
import com.arogyamed.model.PaymentMethod;
import com.arogyamed.model.PaymentStatus;
import com.arogyamed.model.Pharmacist;
import com.arogyamed.repository.MedicineRepository;
import com.arogyamed.repository.OrderRepository;
import com.arogyamed.repository.PatientRepository;
import com.arogyamed.repository.PaymentRepository;
import com.arogyamed.repository.PharmacistRepository;
import com.arogyamed.service.CheckoutService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final PatientRepository patientRepository;
    private final PharmacistRepository pharmacistRepository;
    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public CheckoutServiceImpl(PatientRepository patientRepository,
                               PharmacistRepository pharmacistRepository,
                               MedicineRepository medicineRepository,
                               OrderRepository orderRepository,
                               PaymentRepository paymentRepository) {
        this.patientRepository = patientRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.medicineRepository = medicineRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    // ============ 1. Place the order ============

    @Override
    @Transactional
    public CheckoutResponseDTO checkout(String loginName, CheckoutRequestDTO request) {

        Patient patient = findPatient(loginName);

        if (request.getPaymentMethod() == PaymentMethod.WALLET) {
            throw bad("Wallet payments are not available yet");
        }

        // If the same medicine appears twice, add the quantities together
        Map<Long, Integer> wanted = new LinkedHashMap<>();
        for (CheckoutRequestDTO.Item item : request.getItems()) {
            wanted.merge(item.getMedicineId(), item.getQuantity(), Integer::sum);
        }

        Order order = new Order();
        order.setPatient(patient);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        double total = 0;

        for (Map.Entry<Long, Integer> entry : wanted.entrySet()) {

            Medicine medicine = medicineRepository.findById(entry.getKey())
                    .orElseThrow(() -> bad("Medicine not found: " + entry.getKey()));

            int quantity = entry.getValue();
            int stock = medicine.getStockQuantity() == null ? 0 : medicine.getStockQuantity();

            if (medicine.getExpiryDate() != null && medicine.getExpiryDate().isBefore(LocalDate.now())) {
                throw bad(medicine.getMedicineName() + " is expired and cannot be ordered");
            }
            if (stock < quantity) {
                throw bad(medicine.getMedicineName() + " has only " + stock + " in stock");
            }

            // The price comes from OUR database, never from the browser
            double price = medicine.getPrice() == null ? 0 : medicine.getPrice();
            double subtotal = round2(price * quantity);

            // Reserve the stock now. It is given back if the payment fails or the order is cancelled.
            medicine.setStockQuantity(stock - quantity);

            OrderItem line = new OrderItem();
            line.setOrder(order);
            line.setMedicine(medicine);
            line.setQuantity(quantity);
            line.setPrice(price);
            line.setSubtotal(subtotal);
            order.getItems().add(line);

            total += subtotal;
        }

        order.setTotalAmount(round2(total));

        // Cash on delivery needs no online step, so the order is confirmed right away
        boolean cashOnDelivery = request.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY;
        if (cashOnDelivery) {
            order.setStatus(OrderStatus.CONFIRMED);
        }

        Order savedOrder = orderRepository.save(order);

        Payment payment = new Payment();
        payment.setOrder(savedOrder);
        payment.setAmount(savedOrder.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setPaymentDate(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);

        return toResponse(savedOrder, savedPayment);
    }

    // ============ 2. Demo gateway: finish an online payment ============

    @Override
    @Transactional
    public CheckoutResponseDTO confirmPayment(String loginName, Long orderId, boolean simulateFailure) {

        Patient patient = findPatient(loginName);

        Order order = findOrder(orderId);

        // A patient can only pay for their OWN order
        if (!order.getPatient().getId().equals(patient.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is not your order");
        }

        Payment payment = findPayment(orderId);
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }

        if (payment.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {
            throw bad("Cash on delivery is paid when the order arrives");
        }
        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw bad("This payment is already " + payment.getPaymentStatus());
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw bad("This order is " + order.getStatus() + " and can no longer be paid");
        }

        payment.setPaymentDate(LocalDateTime.now());

        if (simulateFailure) {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            order.setStatus(OrderStatus.CANCELLED);
            giveStockBack(order);
        } else {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            order.setStatus(OrderStatus.CONFIRMED);
        }

        paymentRepository.save(payment);
        orderRepository.save(order);

        return toResponse(order, payment);
    }

    // ============ 3. Patient: my orders ============

    @Override
    @Transactional(readOnly = true)
    public List<CheckoutResponseDTO> myOrders(String loginName) {

        Patient patient = findPatient(loginName);

        List<CheckoutResponseDTO> result = new ArrayList<>();
        for (Order order : orderRepository.findByPatient_IdOrderByOrderDateDesc(patient.getId())) {
            result.add(toResponse(order, findPayment(order.getId())));
        }
        return result;
    }

    // ============ 4. Patient: cancel my order ============

    @Override
    @Transactional
    public CheckoutResponseDTO cancelOrder(String loginName, Long orderId) {

        Patient patient = findPatient(loginName);

        Order order = findOrder(orderId);

        if (!order.getPatient().getId().equals(patient.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is not your order");
        }

        // Once the pharmacy has started preparing it, it is too late to cancel
        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw bad("This order is " + order.getStatus() + " and can no longer be cancelled");
        }

        return cancelInternal(order);
    }

    // ============ 5. Pharmacist: available orders ============

    @Override
    @Transactional(readOnly = true)
    public List<CheckoutResponseDTO> availableOrders() {

        List<CheckoutResponseDTO> result = new ArrayList<>();
        for (Order order : orderRepository
                .findByPharmacistIsNullAndStatusOrderByOrderDateDesc(OrderStatus.CONFIRMED)) {
            result.add(toResponse(order, findPayment(order.getId())));
        }
        return result;
    }

    // ============ 6. Pharmacist: my accepted orders ============

    @Override
    @Transactional(readOnly = true)
    public List<CheckoutResponseDTO> assignedOrders(String loginName) {

        Pharmacist pharmacist = findPharmacist(loginName);

        List<CheckoutResponseDTO> result = new ArrayList<>();
        for (Order order : orderRepository.findByPharmacist_IdOrderByOrderDateDesc(pharmacist.getId())) {
            result.add(toResponse(order, findPayment(order.getId())));
        }
        return result;
    }

    // ============ 7. Pharmacist: accept an order ============

    @Override
    @Transactional
    public CheckoutResponseDTO acceptOrder(String loginName, Long orderId) {

        Pharmacist pharmacist = findPharmacist(loginName);

        // Lock this order row until the transaction ends, so two pharmacists
        // clicking Accept at the same moment cannot both get it
        Order order = entityManager.find(Order.class, orderId, LockModeType.PESSIMISTIC_WRITE);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        if (order.getPharmacist() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Another pharmacist has already accepted this order");
        }
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw bad("Only confirmed orders can be accepted (this one is " + order.getStatus() + ")");
        }

        order.setPharmacist(pharmacist);
        orderRepository.save(order);

        return toResponse(order, findPayment(orderId));
    }

    // ============ 8. Change order status ============

    @Override
    @Transactional
    public CheckoutResponseDTO updateStatus(String loginName, boolean admin, Long orderId, OrderStatus newStatus) {

        Order order = findOrder(orderId);

        // A pharmacist can only change the orders THEY accepted. Admins can change any.
        if (!admin) {
            Pharmacist pharmacist = findPharmacist(loginName);
            if (order.getPharmacist() == null
                    || !order.getPharmacist().getId().equals(pharmacist.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "This order is not assigned to you. Accept it first.");
            }
        }

        OrderStatus current = order.getStatus();
        if (current == OrderStatus.CANCELLED || current == OrderStatus.DELIVERED) {
            throw bad("A " + current + " order cannot be changed");
        }

        // Cancelling by staff gives stock back and settles the payment, exactly like a patient cancel
        if (newStatus == OrderStatus.CANCELLED) {
            return cancelInternal(order);
        }

        order.setStatus(newStatus);
        Payment payment = findPayment(orderId);

        // Cash on delivery: the cash is collected when the order is delivered
        if (newStatus == OrderStatus.DELIVERED
                && payment != null
                && payment.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY
                && payment.getPaymentStatus() == PaymentStatus.PENDING) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);
        }

        orderRepository.save(order);
        return toResponse(order, payment);
    }

    // ============ helpers ============

    // Shared by patient cancel and staff cancel
    private CheckoutResponseDTO cancelInternal(Order order) {

        giveStockBack(order);

        Payment payment = findPayment(order.getId());
        if (payment != null) {
            if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
                payment.setPaymentStatus(PaymentStatus.REFUNDED);   // money was taken, so it is refunded
            } else if (payment.getPaymentStatus() == PaymentStatus.PENDING) {
                payment.setPaymentStatus(PaymentStatus.FAILED);     // nothing was collected
            }
            paymentRepository.save(payment);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return toResponse(order, payment);
    }

    private void giveStockBack(Order order) {
        for (OrderItem line : order.getItems()) {
            Medicine medicine = line.getMedicine();
            int current = medicine.getStockQuantity() == null ? 0 : medicine.getStockQuantity();
            medicine.setStockQuantity(current + line.getQuantity());
        }
    }

    private Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    // Old orders created before the payment feature may have no payment record, so this can be null
    private Payment findPayment(Long orderId) {
        return paymentRepository.findByOrderId(orderId).stream().findFirst().orElse(null);
    }

    // The login token gives us a name (the email). Find the patient who owns it.
    private Patient findPatient(String loginName) {

        if (loginName == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please log in");
        }

        Optional<Patient> byEmail = patientRepository.findByUser_Email(loginName);
        if (byEmail.isPresent()) {
            return byEmail.get();
        }

        // fall back: some setups put the user id in the token instead of the email
        try {
            Optional<Patient> byUserId = patientRepository.findByUser_Id(Long.parseLong(loginName));
            if (byUserId.isPresent()) {
                return byUserId.get();
            }
        } catch (NumberFormatException ignored) {
            // not a number, so it was not a user id
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can place orders");
    }

    // Same idea for the pharmacist
    private Pharmacist findPharmacist(String loginName) {

        if (loginName == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please log in");
        }

        Optional<Pharmacist> byEmail = pharmacistRepository.findByUser_Email(loginName);
        if (byEmail.isPresent()) {
            return byEmail.get();
        }

        try {
            Optional<Pharmacist> byUserId = pharmacistRepository.findByUserId(Long.parseLong(loginName));
            if (byUserId.isPresent()) {
                return byUserId.get();
            }
        } catch (NumberFormatException ignored) {
            // not a number, so it was not a user id
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only pharmacists can do this");
    }

    private CheckoutResponseDTO toResponse(Order order, Payment payment) {

        CheckoutResponseDTO dto = new CheckoutResponseDTO();
        dto.setOrderId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());

        if (order.getPatient() != null && order.getPatient().getUser() != null) {
            dto.setPatientName(order.getPatient().getUser().getFullName());
        }

        if (payment != null) {
            dto.setPaymentId(payment.getId());
            dto.setPaymentMethod(payment.getPaymentMethod());
            dto.setPaymentStatus(payment.getPaymentStatus());
            dto.setTransactionId(payment.getTransactionId());
        }

        List<CheckoutResponseDTO.Line> lines = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            CheckoutResponseDTO.Line line = new CheckoutResponseDTO.Line();
            line.setMedicineId(item.getMedicine().getId());
            line.setMedicineName(item.getMedicine().getMedicineName());
            line.setQuantity(item.getQuantity());
            line.setPrice(item.getPrice());
            line.setSubtotal(item.getSubtotal());
            lines.add(line);
        }
        dto.setItems(lines);

        return dto;
    }

    private ResponseStatusException bad(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}