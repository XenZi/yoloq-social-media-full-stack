package com.example.yoloq.elastic_services.impl;


import com.example.yoloq.elastic_models.PostDocument;
import com.example.yoloq.elastic_repository.PostDocumentRepository;
import com.example.yoloq.elastic_services.MinioFileService;
import com.example.yoloq.elastic_services.PostIndexingService;
import com.example.yoloq.exception.LoadingException;
import com.example.yoloq.exception.StorageException;
import com.example.yoloq.models.Post;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.language.detect.LanguageDetector;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostIndexingServiceImpl implements PostIndexingService {

    private final PostDocumentRepository postDocumentRepository;
    private final MinioFileService minioFileService;
    private final LanguageDetector languageDetector;


    @Override
    @Transactional
    public PostDocument indexDocument(Post post, MultipartFile documentFile) {
        var newPostDocument = new PostDocument();
        newPostDocument.setTitle(post.getTitle());
        var documentContent = extractDocumentContent(documentFile);
        if (detectLanguage(documentContent).equals("SR")) {
            newPostDocument.setContentSr(documentContent);
        } else {
            newPostDocument.setContentEn(documentContent);
        }
        newPostDocument.setContent(post.getContent());
        var serverFilename = minioFileService.store(documentFile, UUID.randomUUID().toString());
        newPostDocument.setServerFilename(serverFilename);


        newPostDocument.setDatabaseId(post.getId());
        newPostDocument.setTotalLikes(0);
        newPostDocument.setTotalComments(0);
        if (post.getPostedInGroup() != null) {
            newPostDocument.setGroupID(post.getPostedInGroup().getId());
        }
        postDocumentRepository.save(newPostDocument);
        return newPostDocument;
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
