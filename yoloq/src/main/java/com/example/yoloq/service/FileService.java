package com.example.yoloq.service;

import com.example.yoloq.models.Image;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public Image uploadImage(MultipartFile file);

    public Resource getImage(String fileName);
}
