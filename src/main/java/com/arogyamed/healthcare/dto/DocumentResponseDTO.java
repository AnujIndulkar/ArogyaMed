package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.DocumentModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}