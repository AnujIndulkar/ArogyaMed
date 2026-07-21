package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.DocumentRequestDTO;
import com.arogyamed.healthcare.dto.DocumentResponseDTO;
import com.arogyamed.healthcare.model.Document;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.model.DocumentModule;
import com.arogyamed.healthcare.repository.DocumentRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final UserRepository userRepository;

    @Value("${document.upload.path}")
    private String uploadPath;

    @Override
    public DocumentResponseDTO uploadDocument(MultipartFile file, DocumentRequestDTO requestDTO) {

        try {

            User user = userRepository.findById(requestDTO.getUploadedBy()).orElseThrow(() ->
                            new RuntimeException("User not found with ID : " + requestDTO.getUploadedBy()));

            System.out.println("User Found : " + user.getId());

            String originalFileName = file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            System.out.println("Extension : " + extension);

            String storedFileName = UUID.randomUUID() + extension;

            System.out.println("Stored File Name : " + storedFileName);

            Path directoryPath = Paths.get(uploadPath);

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Upload directory created.");
            }

            Path targetPath = directoryPath.resolve(storedFileName);

            System.out.println("Target Path : " + targetPath);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File copied successfully.");

            Document document = Document.builder()
                    .fileName(originalFileName)
                    .storedFileName(storedFileName)
                    .fileType(file.getContentType())
                    .fileExtension(extension.replace(".", ""))
                    .fileSize(file.getSize())
                    .storagePath(targetPath.toString())
                    .documentModule(requestDTO.getDocumentModule())
                    .referenceId(requestDTO.getReferenceId())
                    .uploadedBy(user)
                    .uploadedAt(LocalDateTime.now())
                    .active(true)
                    .build();

            System.out.println("Saving document...");

            Document savedDocument = documentRepository.save(document);

            System.out.println("Document Saved Successfully. ID = " + savedDocument.getId());

            return convertToResponseDTO(savedDocument);

        } catch (Exception e) {

            System.out.println("========== ERROR ==========");
            e.printStackTrace();

            throw new RuntimeException("Failed to upload document.", e);
        }
    }

    @Override
    public DocumentResponseDTO getDocumentById(Long documentId) {

        Document document = documentRepository.findById(documentId).orElseThrow(() ->
                new RuntimeException("Document not found with ID : " + documentId));

        return convertToResponseDTO(document);
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByModule(DocumentModule documentModule, Long referenceId) {

        return documentRepository.findByDocumentModuleAndReferenceIdAndActiveTrue(documentModule, referenceId)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByUser(Long userId) {

        return documentRepository.findByUploadedByIdAndActiveTrue(userId)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    public Resource downloadDocument(Long documentId) {

        try {
            Document document = documentRepository.findById(documentId).orElseThrow(() ->
                            new RuntimeException("Document not found with ID : " + documentId));

            Path filePath = Paths.get(document.getStoragePath());

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("Document not found.");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to download document.", e);

        }
    }

    @Override
    public void deleteDocument(Long documentId) {

        Document document = documentRepository.findById(documentId).orElseThrow(() ->
                        new RuntimeException("Document not found with ID: " + documentId));

        document.setActive(false);

        documentRepository.save(document);
    }

    private DocumentResponseDTO convertToResponseDTO(Document document) {

        return DocumentResponseDTO.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .fileType(document.getFileType())
                .fileSize(document.getFileSize())
                .fileSizeText(formatFileSize(document.getFileSize()))
                .documentModule(document.getDocumentModule())
                .uploadedAt(document.getUploadedAt())
                .downloadUrl(generateDownloadUrl(document.getId()))
                .build();
    }

    private String formatFileSize(Long bytes) {

        if (bytes == null) {
            return "0 Bytes";
        }

        double size = bytes;

        if (size < 1024) {
            return bytes + " Bytes";
        }

        size = size / 1024;

        if (size < 1024) {
            return String.format("%.2f KB", size);
        }

        size = size / 1024;

        if (size < 1024) {
            return String.format("%.2f MB", size);
        }

        size = size / 1024;

        return String.format("%.2f GB", size);
    }

    private String generateDownloadUrl(Long documentId) {

        return "/api/documents/download/" + documentId;
    }
}