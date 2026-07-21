package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.DocumentModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequestDTO {

    private DocumentModule documentModule;

    private Long referenceId;

    private Long uploadedBy;
}
