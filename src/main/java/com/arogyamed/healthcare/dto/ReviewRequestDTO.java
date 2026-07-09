package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.ReviewType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {

    private Long patientId;

    private ReviewType reviewType;

    private Long doctorId;

    private Long pharmacistId;

    private Long ambulanceId;

    private Long deliveryPartnerId;

    private Long medicineId;

    private Integer rating;

    private String comment;

}