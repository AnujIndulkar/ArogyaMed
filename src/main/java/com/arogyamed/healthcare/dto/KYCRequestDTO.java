package com.arogyamed.healthcare.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KYCRequestDTO {

    private Long userId;

    private String documentType;

    private String documentNumber;

    private String documentUrl;

}
