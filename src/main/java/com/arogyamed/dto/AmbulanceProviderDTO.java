package com.arogyamed.dto;

import java.time.LocalDateTime;

public class AmbulanceProviderDTO {

    /** An open emergency request that any available ambulance can accept. */
    public record SosRequest(
            Long id,
            String patientName,
            String emergencyType,
            String location,
            Double latitude,
            Double longitude,
            LocalDateTime createdAt) {
    }

    /** Numbers for the provider dashboard. */
    public record Summary(
            String ambulanceStatus,
            long openRequests,
            long activeTrips,
            long completedTrips,
            long totalTrips) {
    }
}