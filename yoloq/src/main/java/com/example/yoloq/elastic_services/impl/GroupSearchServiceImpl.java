package com.example.yoloq.elastic_services.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_services.GroupSearchService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GroupSearchServiceImpl implements GroupSearchService {

    private final ElasticsearchOperations elasticsearchTemplate;


    @Override
    public List<GroupDocument> searchGroupsByName(String name) {
        return searchGroups("name", name);
    }

    @Override
    public List<GroupDocument> searchGroupsByDescription(String description) {
        return searchGroups("description", description);
    }


    private List<GroupDocument> searchGroups(String field, String value) {
        var query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(field, value).analyzer("serbian_normalizer"))
                .build();

        SearchHits<GroupDocument> searchHits = elasticsearchTemplate.search(query, GroupDocument.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
