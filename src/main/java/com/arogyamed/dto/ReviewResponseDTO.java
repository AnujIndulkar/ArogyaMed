package com.arogyamed.dto;

import com.arogyamed.model.ReviewType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {

    private Long id;

    private Long patientId;

    private String patientName;

    private ReviewType reviewType;

    private Integer rating;

    private String comment;

    private LocalDateTime reviewDate;

}
