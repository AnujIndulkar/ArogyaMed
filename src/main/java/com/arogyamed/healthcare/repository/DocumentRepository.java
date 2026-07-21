package com.arogyamed.healthcare.repository;

import com.arogyamed.healthcare.model.Document;
import com.arogyamed.healthcare.model.DocumentModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByDocumentModuleAndReferenceIdAndActiveTrue(DocumentModule documentModule, Long referenceId);

    List<Document> findByUploadedByIdAndActiveTrue(Long userId);

    List<Document> findByActiveTrue();

    boolean existsByStoredFileName(String storedFileName);
}
