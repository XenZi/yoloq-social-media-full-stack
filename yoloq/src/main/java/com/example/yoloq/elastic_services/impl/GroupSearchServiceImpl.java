package com.example.yoloq.elastic_services.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_services.GroupSearchService;
import com.example.yoloq.models.dto.requests.SearchGroupsBasedOnNumberOfPostsDTO;
import lombok.RequiredArgsConstructor;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GroupSearchServiceImpl implements GroupSearchService {

    private final ElasticsearchOperations elasticsearchTemplate;


    @Override
    public List<GroupDocument> searchGroupsByName(String name) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(simpleSearchForName(name));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<GroupDocument> searchGroupsByDescription(String description) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(simpleSearchForDescription(description));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<GroupDocument> searchGroupsByPDFContent(String content) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(simpleSearchForPDFDescription(content));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<GroupDocument> searchGroupsByPosts(SearchGroupsBasedOnNumberOfPostsDTO data) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(searchByNumPostsRange(data.getGreaterThan(), data.getLessThan()));
        return runQuery(searchQueryBuilder.build());

    }

    @Override
    public List<GroupDocument> searchGroupsCombined(String name, String description, String pdfContent, Boolean useAndOperator) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildComplexSearchQuery(name, description, pdfContent, useAndOperator));
        return runQuery(searchQueryBuilder.build());
    }

    private List<GroupDocument> runQuery(NativeQuery searchQuery) {
        SearchHits<GroupDocument> searchHits = elasticsearchTemplate.search(searchQuery, GroupDocument.class,
                IndexCoordinates.of("groups"));
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }


    public Query searchByNumPostsRange(Integer minPosts, Integer maxPosts) {
        return RangeQuery.of(q -> {
            if (minPosts != null) {
                q.field("numPosts").gte(JsonData.of(minPosts));
            }
            if (maxPosts != null) {
                q.field("numPosts").lte(JsonData.of(maxPosts));
            }
            return q;
        })._toQuery();
    }


    private Query buildComplexSearchQuery(String name, String description, String pdfContent, Boolean useAndOperator) {
        return BoolQuery.of(q -> {
            if (useAndOperator) {
                q.must(mb -> mb.bool(b -> {
                    if (name != null && !name.isEmpty()) {
                        b.must(sb -> sb.match(m -> m.field("name").query(name).analyzer("serbian_simple")));
                    }
                    if (description != null && !description.isEmpty()) {
                        b.must(sb -> sb.match(m -> m.field("description").query(description).analyzer("serbian_simple")));
                    }
                    if (pdfContent != null && !pdfContent.isEmpty()) {
                        b.should(sb -> sb.match(m -> m.field("content_sr").query(pdfContent).analyzer("serbian_simple")));
                        b.should(sb -> sb.match(m -> m.field("content_en").query(pdfContent).analyzer("english")));
                    }
                    return b;
                }));
            } else {
                q.should(mb -> mb.bool(b -> {
                    if (name != null && !name.isEmpty()) {
                        b.should(sb -> sb.match(m -> m.field("name").query(name).analyzer("serbian_simple")));
                    }
                    if (description != null && !description.isEmpty()) {
                        b.should(sb -> sb.match(m -> m.field("description").query(description).analyzer("serbian_simple")));
                    }
                    if (pdfContent != null && !pdfContent.isEmpty()) {
                        b.should(sb -> sb.match(m -> m.field("content_sr").query(pdfContent).analyzer("serbian_simple")));
                        b.should(sb -> sb.match(m -> m.field("content_en").query(pdfContent).analyzer("english")));
                    }
                    return b;
                }));
            }
            return q;
        })._toQuery();
    }


    private Query simpleSearchForName(String name) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("name").query(name).analyzer("serbian_simple")));
            return b;
        })))._toQuery();
    }

    private Query simpleSearchForDescription(String description) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("description").query(description).analyzer("serbian_simple")));
            return b;
        })))._toQuery();
    }

    private Query simpleSearchForPDFDescription(String description) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("content_sr").query(description).analyzer("serbian_simple")));
            b.should(sb -> sb.match(m -> m.field("content_en").query(description).analyzer("english")));
            return b;
        })))._toQuery();
    }
}
