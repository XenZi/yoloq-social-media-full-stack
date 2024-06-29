package com.example.yoloq.elastic_services.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_models.PostDocument;
import com.example.yoloq.elastic_services.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {
    private final ElasticsearchOperations elasticsearchTemplate;
    @Override
    public List<PostDocument> getPostsByPostName(String postName) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(List.of(postName)));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByPostContent(String content) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(List.of(content)));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByPDFContent(String content) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(List.of(content)));
        return runQuery(searchQueryBuilder.build());
    }


    private List<PostDocument> runQuery(NativeQuery searchQuery) {
        SearchHits<PostDocument> searchHits = elasticsearchTemplate.search(searchQuery, PostDocument.class,
                IndexCoordinates.of("groups"));
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    private Query buildSimpleSearchQuery(List<String> tokens) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            tokens.forEach(token -> {
                b.should(sb -> sb.match(m -> m.field("title").query(token).analyzer("serbian_simple")));
                b.should(sb -> sb.match(m -> m.field("content").query(token).analyzer("serbian_simple")));
                b.should(sb -> sb.match(m -> m.field("content_sr").query(token).analyzer("serbian_simple")));
                b.should(sb -> sb.match(m -> m.field("content_en").query(token).analyzer("english")));
            });
            return b;
        })))._toQuery();
    }

}
