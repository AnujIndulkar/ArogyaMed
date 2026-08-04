package com.arogyamed.dto;

import com.arogyamed.model.KYCStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KYCResponseDTO {

    private Long id;

    private Long userId;

    private String userName;

    private String role;

    private String documentType;

    private String documentNumber;

    private String documentUrl;

    private KYCStatus status;

    private String remarks;

    private LocalDateTime submittedAt;

    private LocalDateTime verifiedAt;

}
