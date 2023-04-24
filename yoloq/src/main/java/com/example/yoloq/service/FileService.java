package com.example.yoloq.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String uploadImage(MultipartFile file);
    public Resource getImage(String fileName);
}
