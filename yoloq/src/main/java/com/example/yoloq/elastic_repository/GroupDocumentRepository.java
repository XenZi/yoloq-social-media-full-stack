package com.example.yoloq.elastic_repository;

import com.example.yoloq.elastic_models.GroupDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  GroupDocumentRepository extends ElasticsearchRepository<GroupDocument, String>  {
}
