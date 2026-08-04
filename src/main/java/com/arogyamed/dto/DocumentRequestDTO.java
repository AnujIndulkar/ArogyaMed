package com.arogyamed.dto;

import com.arogyamed.model.DocumentModule;
import com.arogyamed.model.DocumentType;
import com.arogyamed.model.Role;
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
