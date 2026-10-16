package com.arogyamed.controller;

import com.arogyamed.dto.CheckoutRequestDTO;
import com.arogyamed.dto.CheckoutResponseDTO;
import com.arogyamed.dto.DemoPaymentRequestDTO;
import com.arogyamed.dto.StatusUpdateRequestDTO;
import com.arogyamed.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // Patient places an order from the cart
    @PostMapping("/checkout")
    public CheckoutResponseDTO checkout(@Valid @RequestBody CheckoutRequestDTO request,
                                        Authentication authentication) {
        return checkoutService.checkout(authentication.getName(), request);
    }

    // Demo gateway: finish (or fail) an online payment
    @PostMapping("/{id}/pay")
    public CheckoutResponseDTO pay(@PathVariable Long id,
                                   @RequestBody(required = false) DemoPaymentRequestDTO body,
                                   Authentication authentication) {
        boolean fail = body != null && body.isSimulateFailure();
        return checkoutService.confirmPayment(authentication.getName(), id, fail);
    }

    // Patient: only MY orders
    @GetMapping("/my")
    public List<CheckoutResponseDTO> myOrders(Authentication authentication) {
        return checkoutService.myOrders(authentication.getName());
    }

    // Patient: cancel my order
    @PostMapping("/{id}/cancel")
    public CheckoutResponseDTO cancel(@PathVariable Long id, Authentication authentication) {
        return checkoutService.cancelOrder(authentication.getName(), id);
    }

    // Pharmacist: orders waiting for someone to accept them
    @GetMapping("/available")
    public List<CheckoutResponseDTO> available() {
        return checkoutService.availableOrders();
    }

    // Pharmacist: orders I accepted
    @GetMapping("/assigned")
    public List<CheckoutResponseDTO> assigned(Authentication authentication) {
        return checkoutService.assignedOrders(authentication.getName());
    }

    // Pharmacist: accept an available order
    @PostMapping("/{id}/accept")
    public CheckoutResponseDTO accept(@PathVariable Long id, Authentication authentication) {
        return checkoutService.acceptOrder(authentication.getName(), id);
    }

    // Pharmacist (own orders) or admin (any order): change status
    @PatchMapping("/{id}/status")
    public CheckoutResponseDTO updateStatus(@PathVariable Long id,
                                            @Valid @RequestBody StatusUpdateRequestDTO body,
                                            Authentication authentication) {
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return checkoutService.updateStatus(authentication.getName(), admin, id, body.getStatus());
    }
}