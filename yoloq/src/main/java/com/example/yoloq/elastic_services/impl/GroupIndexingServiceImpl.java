package com.example.yoloq.elastic_services.impl;

import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_repository.GroupDocumentRepository;
import com.example.yoloq.elastic_services.GroupIndexingService;
import com.example.yoloq.elastic_services.MinioFileService;
import com.example.yoloq.exception.LoadingException;
import com.example.yoloq.exception.StorageException;
import com.example.yoloq.models.Group;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.apache.tika.Tika;
import org.apache.tika.language.detect.LanguageDetector;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupIndexingServiceImpl implements GroupIndexingService {
    private final GroupDocumentRepository groupDocumentRepository;
    private final MinioFileService minioFileService;
    private final LanguageDetector languageDetector;


    @Override
    @Transactional
    public GroupDocument indexDocument(Group group, MultipartFile documentFile) {
        var newGroupDocument = new GroupDocument();

        var title = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
        newGroupDocument.setName(group.getName());

        var documentContent = extractDocumentContent(documentFile);
        if (detectLanguage(documentContent).equals("SR")) {
            newGroupDocument.setContentSr(documentContent);
        } else {
            newGroupDocument.setContentEn(documentContent);
        }
        newGroupDocument.setDescription(group.getDescription());


        var serverFilename = minioFileService.store(documentFile, UUID.randomUUID().toString());
        newGroupDocument.setServerFilename(serverFilename);


        newGroupDocument.setDatabaseId(group.getId());
        groupDocumentRepository.save(newGroupDocument);
        return newGroupDocument;
    }

    private String extractDocumentContent(MultipartFile multipartPdfFile) {
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    private String detectLanguage(String text) {
        var detectedLanguage = languageDetector.detect(text).getLanguage().toUpperCase();
        if (detectedLanguage.equals("HR")) {
            detectedLanguage = "SR";
        }

        return detectedLanguage;
    }

    private String detectMimeType(MultipartFile file) {
        var contentAnalyzer = new Tika();

        String trueMimeType;
        String specifiedMimeType;
        try {
            trueMimeType = contentAnalyzer.detect(file.getBytes());
            specifiedMimeType =
                    Files.probeContentType(Path.of(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to detect mime type for file.");
        }

        if (!trueMimeType.equals(specifiedMimeType) &&
                !(trueMimeType.contains("zip") && specifiedMimeType.contains("zip"))) {
            throw new StorageException("True mime type is different from specified one, aborting.");
        }

        return trueMimeType;
    }

}
