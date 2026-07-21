package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.DocumentRequestDTO;
import com.arogyamed.healthcare.dto.DocumentResponseDTO;
import com.arogyamed.healthcare.model.DocumentModule;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocumentResponseDTO uploadDocument(MultipartFile file, DocumentRequestDTO requestDTO);

    DocumentResponseDTO getDocumentById(Long documentId);

    List<DocumentResponseDTO> getDocumentsByModule(DocumentModule documentModule, Long referenceId);

    List<DocumentResponseDTO> getDocumentsByUser(Long userId);

    Resource downloadDocument(Long documentId);

    void deleteDocument(Long documentId);


}
