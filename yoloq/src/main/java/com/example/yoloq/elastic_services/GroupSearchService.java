package com.example.yoloq.elastic_services;


import com.example.yoloq.elastic_models.GroupDocument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupSearchService {

    List<GroupDocument> searchGroupsByName(String name);
    List<GroupDocument> searchGroupsByDescription(String description);
}
