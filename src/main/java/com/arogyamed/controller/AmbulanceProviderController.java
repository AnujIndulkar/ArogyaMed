package com.arogyamed.controller;

import com.arogyamed.dto.AmbulanceBookingResponseDTO;
import com.arogyamed.dto.AmbulanceProviderDTO;
import com.arogyamed.dto.AmbulanceResponseDTO;
import com.arogyamed.model.AmbulanceStatus;
import com.arogyamed.model.BookingStatus;
import com.arogyamed.service.AmbulanceProviderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambulance-provider")
public class AmbulanceProviderController {

    private final AmbulanceProviderService service;

    public AmbulanceProviderController(AmbulanceProviderService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public AmbulanceResponseDTO me(Authentication authentication) {
        return service.myAmbulance(authentication.getName());
    }

    // AVAILABLE or MAINTENANCE (on duty is set automatically)
    @PatchMapping("/status")
    public AmbulanceResponseDTO changeStatus(@RequestParam AmbulanceStatus status, Authentication authentication) {
        return service.changeAmbulanceStatus(authentication.getName(), status);
    }

    @GetMapping("/summary")
    public AmbulanceProviderDTO.Summary summary(Authentication authentication) {
        return service.summary(authentication.getName());
    }

    // emergency requests nobody has accepted yet
    @GetMapping("/requests")
    public List<AmbulanceProviderDTO.SosRequest> requests(Authentication authentication) {
        return service.openRequests(authentication.getName());
    }

    @PostMapping("/requests/{sosId}/accept")
    public AmbulanceBookingResponseDTO accept(@PathVariable Long sosId,
                                              @RequestParam(required = false) Integer eta,
                                              Authentication authentication) {
        return service.acceptRequest(authentication.getName(), sosId, eta);
    }

    @GetMapping("/bookings")
    public List<AmbulanceBookingResponseDTO> bookings(Authentication authentication) {
        return service.myBookings(authentication.getName());
    }

    @PatchMapping("/bookings/{id}/status")
    public AmbulanceBookingResponseDTO changeBookingStatus(@PathVariable Long id,
                                                           @RequestParam BookingStatus status,
                                                           Authentication authentication) {
        return service.changeBookingStatus(authentication.getName(), id, status);
    }
}