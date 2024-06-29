package com.example.yoloq.elastic_repository;

import com.example.yoloq.elastic_models.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, String> {
}
