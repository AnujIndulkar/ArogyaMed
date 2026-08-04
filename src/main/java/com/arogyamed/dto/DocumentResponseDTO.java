package com.arogyamed.dto;

import com.arogyamed.model.DocumentModule;
import com.arogyamed.model.DocumentType;
import com.arogyamed.model.Role;
import com.arogyamed.model.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDTO {

    private Long id;

    private String fileName;

    private String fileType;

    private Long fileSize;

    // Human-readable file size (KB,MB,GB)
    private String fileSizeText;

    private DocumentModule documentModule;

    private LocalDateTime uploadedAt;

    // URL used by the frontend to download the document
    private String downloadUrl;

    private DocumentType documentType;

    private String documentNumber;

    private Role role;

    private Long uploadedById;

    private String uploadedByName;

    private VerificationStatus verificationStatus;

    private Long verifiedById;

    private String verifiedByName;

    private LocalDateTime verifiedAt;

    private LocalDate expiryDate;

    private Boolean expired;

    private String rejectionReason;
}