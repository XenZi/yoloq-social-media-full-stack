package com.example.yoloq.elastic_services;

import com.example.yoloq.elastic_models.PostDocument;
import com.example.yoloq.models.dto.requests.SearchPostsBasedOnNumberOfCommentsDTO;
import com.example.yoloq.models.dto.requests.SearchPostsBasedOnNumberOfLikesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostSearchService {
    List<PostDocument> getPostsByPostName(String postName);
    List<PostDocument> getPostsByPostContent(String content);
    List<PostDocument> getPostsByPDFContent(String content);
    List<PostDocument> getPostsByNumberOfLikes(SearchPostsBasedOnNumberOfLikesDTO criteria);
    List<PostDocument> getPostsByNumberOfComments(SearchPostsBasedOnNumberOfCommentsDTO criteria);
    List<PostDocument> getPostsCombined(String postName, String postContent, String pdfContent, Boolean useAndQuery);
    List<PostDocument> getPostsByTitlePhrase(String phrase);
    List<PostDocument> getPostsByDescriptionPhrase(String phrase);
    List<PostDocument> getPostsByNameFuzzy(String name);
    List<PostDocument> getPostsByDescriptionFuzzy(String name);
}
