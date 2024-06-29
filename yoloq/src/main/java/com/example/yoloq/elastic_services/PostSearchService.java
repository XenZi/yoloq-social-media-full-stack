package com.example.yoloq.elastic_services;

import com.example.yoloq.elastic_models.PostDocument;

import java.util.List;

public interface PostSearchService {
    List<PostDocument> getPostsByPostName(String postName);
    List<PostDocument> getPostsByPostContent(String content);
    List<PostDocument> getPostsByPDFContent(String content);
}
