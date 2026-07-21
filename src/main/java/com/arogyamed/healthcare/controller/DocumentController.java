package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.DocumentRequestDTO;
import com.arogyamed.healthcare.dto.DocumentResponseDTO;
import com.arogyamed.healthcare.model.DocumentModule;
import com.arogyamed.healthcare.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @ModelAttribute DocumentRequestDTO requestDTO) {

        return ResponseEntity.ok(documentService.uploadDocument(file, requestDTO));

    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable Long documentId) {

        return ResponseEntity.ok(documentService.getDocumentById(documentId));
    }

    @GetMapping("/module/{documentModule}/{referenceId}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByModule(
            @PathVariable DocumentModule documentModule,
            @PathVariable Long referenceId) {

        return ResponseEntity.ok(documentService.getDocumentsByModule(documentModule, referenceId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByUser(@PathVariable Long userId) {

        return ResponseEntity.ok(documentService.getDocumentsByUser(userId));
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {

        Resource resource = documentService.downloadDocument(documentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<String> deleteDocument(
            @PathVariable Long documentId) {

        documentService.deleteDocument(documentId);

        return ResponseEntity.ok("Document deleted successfully.");
    }
}
