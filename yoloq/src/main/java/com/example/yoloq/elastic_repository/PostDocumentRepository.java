package com.example.yoloq.elastic_repository;

import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_models.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, String> {
    Optional<PostDocument> findByDatabaseId(Integer databaseId);
}
