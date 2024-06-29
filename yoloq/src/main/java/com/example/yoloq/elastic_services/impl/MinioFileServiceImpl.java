package com.example.yoloq.elastic_services.impl;

import com.example.yoloq.elastic_services.MinioFileService;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.exception.StorageException;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MinioFileServiceImpl implements MinioFileService {
    private final MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucketName;

    @Override
    public String store(MultipartFile file, String serverFilename) {
        System.out.println("Bucket Name: " + bucketName);
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        var originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new StorageException("Original filename is invalid.");
        }

        var originalFilenameTokens = originalFilename.split("\\.");
        var extension = originalFilenameTokens[originalFilenameTokens.length - 1];
        var objectName = serverFilename + "." + extension;

        try {
            // Ensure the bucket exists
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                System.out.println("Bucket does not exist. Creating bucket: " + bucketName);
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            try (InputStream inputStream = file.getInputStream()) {
                System.out.println("Storing file to Minio: " + objectName);
                PutObjectArgs args = PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .headers(Collections.singletonMap("Content-Disposition",
                                "attachment; filename=\"" + originalFilename + "\""))
                        .stream(inputStream, file.getSize(), -1)
                        .build();
                minioClient.putObject(args);
                System.out.println("File stored successfully: " + objectName);
            }
        } catch (Exception e) {
            System.err.println("Exception during file upload: " + e.getMessage());
            e.printStackTrace();
            throw new StorageException("Error while storing file in Minio: " + e.getMessage());
        }

        return objectName;
    }

    @Override
    public void delete(String serverFilename) {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(serverFilename)
                    .build();
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new StorageException("Error while deleting " + serverFilename + " from Minio.");
        }
    }

    @Override
    public GetObjectResponse loadAsResource(String serverFilename) {
        try {
            // Get signed URL
            var argsDownload = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(serverFilename)
                    .expiry(60 * 5) // in seconds
                    .build();
            var downloadUrl = minioClient.getPresignedObjectUrl(argsDownload);
            System.out.println(downloadUrl);

            // Get object response
            var args = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(serverFilename)
                    .build();
            return minioClient.getObject(args);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Document " + serverFilename + " does not exist.");
        }
    }
}
