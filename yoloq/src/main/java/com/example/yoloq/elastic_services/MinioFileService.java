package com.example.yoloq.elastic_services;

import io.minio.GetObjectResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MinioFileService {
    String store(MultipartFile file, String serverFilename);
    void delete(String serverFilename);
    GetObjectResponse loadAsResource(String serverFilename);

}
