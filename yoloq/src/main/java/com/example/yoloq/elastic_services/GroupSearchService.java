package com.example.yoloq.elastic_services;


import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.models.dto.requests.SearchGroupsBasedOnNumberOfPostsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupSearchService {

    List<GroupDocument> searchGroupsByName(String name);
    List<GroupDocument> searchGroupsByDescription(String description);
    List<GroupDocument> searchGroupsByPDFContent(String content);
    List<GroupDocument> searchGroupsByPosts(SearchGroupsBasedOnNumberOfPostsDTO data);
    List<GroupDocument> searchGroupsCombined(String name, String description, String pdfContent, Boolean useAndOperator);
    List<GroupDocument> searchGroupsByNamePhrase(String phrase);
    List<GroupDocument> searchGroupsByDescriptionPhrase(String phrase);
    List<GroupDocument> searchGroupsByNameFuzzy(String name);
    List<GroupDocument> searchGroupsByDescriptionFuzzy(String description);

}
