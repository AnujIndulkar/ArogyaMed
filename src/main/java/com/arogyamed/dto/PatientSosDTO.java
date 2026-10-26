package com.arogyamed.dto;

import com.arogyamed.model.SOSStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PatientSosDTO {

    /**
     * What the patient's phone sends. The patient is found from the login, never from this.
     * Coordinates are optional: an emergency must still go through if GPS is off or denied.
     * When they are sent they must be real coordinates.
     */
    public record CreateRequest(
            @DecimalMin(value = "-90.0", message = "Latitude is not valid")
            @DecimalMax(value = "90.0", message = "Latitude is not valid")
            Double latitude,

            @DecimalMin(value = "-180.0", message = "Longitude is not valid")
            @DecimalMax(value = "180.0", message = "Longitude is not valid")
            Double longitude,

            @Size(max = 255, message = "Location is too long") String location,
            @Size(max = 100, message = "Emergency type is too long") String emergencyType) {
    }

    /** The patient's current emergency, with the ambulance details once one accepts. */
    public record Status(
            Long sosId,
            SOSStatus status,
            String emergencyType,
            String location,
            LocalDateTime createdAt,
            String bookingStatus,
            String ambulanceNumber,
            String driverName,
            String driverPhone,
            Integer etaMinutes) {
    }
}