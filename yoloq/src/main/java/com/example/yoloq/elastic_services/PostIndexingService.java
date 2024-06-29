package com.example.yoloq.elastic_services;


import com.example.yoloq.elastic_models.PostDocument;
import com.example.yoloq.models.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostIndexingService {

    PostDocument indexDocument(Post post, MultipartFile documentFile);
}
