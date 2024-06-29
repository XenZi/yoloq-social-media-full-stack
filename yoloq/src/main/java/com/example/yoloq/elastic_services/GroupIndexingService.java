package com.example.yoloq.elastic_services;


import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.models.Group;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface GroupIndexingService {
    GroupDocument indexDocument(Group group, MultipartFile documentFile);
}
