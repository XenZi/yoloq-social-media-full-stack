package com.example.yoloq.elastic_repository;

import com.example.yoloq.elastic_models.GroupDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  GroupDocumentRepository extends ElasticsearchRepository<GroupDocument, String>  {

    Optional<GroupDocument> findByDatabaseId(Integer databaseId);



}
