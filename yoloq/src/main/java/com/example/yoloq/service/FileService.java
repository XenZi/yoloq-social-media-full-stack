package com.example.yoloq.service;

import com.example.yoloq.models.Image;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.User;
import io.minio.GetObjectResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
     Image uploadImage(MultipartFile file);
     Resource getImage(String fileName);

}
