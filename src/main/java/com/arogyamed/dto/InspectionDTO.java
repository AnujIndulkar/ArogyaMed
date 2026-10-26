package com.arogyamed.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class InspectionDTO {

    /** One medicine batch that has not been inspected yet. */
    public record PendingBatch(
            Long medicineId,
            String medicineName,
            String companyName,
            String batchNumber,
            LocalDate manufacturingDate,
            LocalDate expiryDate) {
    }

    /** What the inspector submits. The inspector's identity comes from the login, never from here. */
    public record SubmitRequest(
            @NotNull(message = "Choose a medicine batch") Long medicineId,
            boolean packagingVerified,
            boolean sealVerified,
            boolean temperatureVerified,
            boolean expiryVerified,
            @Size(max = 1000, message = "Remarks can be at most 1000 characters") String remarks,
            boolean approve) {
    }

    /** Numbers for the inspector dashboard. */
    public record Summary(long pending, long myApproved, long myRejected, long myTotal) {
    }
}