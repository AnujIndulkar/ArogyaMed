package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.DocumentModule;
import com.arogyamed.healthcare.model.DocumentType;
import com.arogyamed.healthcare.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequestDTO {

    private DocumentModule documentModule;

    private Long referenceId;

    private Long uploadedBy;

    private DocumentType documentType;

    private String documentNumber;

    private Role role;

    private LocalDate expiryDate;
}
