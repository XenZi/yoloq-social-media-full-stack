package com.example.yoloq.elastic_services.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_models.PostDocument;
import com.example.yoloq.elastic_services.PostSearchService;
import com.example.yoloq.models.dto.requests.SearchPostsBasedOnNumberOfCommentsDTO;
import com.example.yoloq.models.dto.requests.SearchPostsBasedOnNumberOfLikesDTO;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
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
                new NativeQueryBuilder().withQuery(simpleSearchForTitle(postName));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByPostContent(String content) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(simpleSearchForContent(content));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByPDFContent(String content) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(simpleSearchForPDFContent(content));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByNumberOfLikes(SearchPostsBasedOnNumberOfLikesDTO criteria) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(searchByNumOfLikesRange(criteria.getGreaterThan(), criteria.getLessThan()));
        return runQuery(searchQueryBuilder.build());

    }

    @Override
    public List<PostDocument> getPostsByNumberOfComments(SearchPostsBasedOnNumberOfCommentsDTO criteria) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(searchPostsByNumberOfComments(criteria.getGreaterThan(), criteria.getLessThan()));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsCombined(String postName, String postContent, String pdfContent, Boolean useAndQuery) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildComplexSearchQuery(postName, postContent, pdfContent, useAndQuery));
        return runQuery(searchQueryBuilder.build());

    }

    @Override
    public List<PostDocument> getPostsByTitlePhrase(String phrase) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(phraseSearchForTitle(phrase));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByDescriptionPhrase(String phrase) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(phraseSearchForContent(phrase));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByNameFuzzy(String name) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(fuzzySearchForTitle(name));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public List<PostDocument> getPostsByDescriptionFuzzy(String name) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(fuzzySearchForContent(name));
        return runQuery(searchQueryBuilder.build());
    }

    private Query buildComplexSearchQuery(String title, String content, String pdfContent, Boolean useAndOperator) {
        return BoolQuery.of(q -> {
            if (useAndOperator) {
                q.must(mb -> mb.bool(b -> {
                    if (title != null && !title.isEmpty()) {
                        b.must(sb -> sb.bool(subBool -> subBool
                                .should(subShould -> subShould.matchPhrase(m -> m.field("title").query(title)))));
                    }
                    if (content != null && !content.isEmpty()) {
                        b.must(sb -> sb.bool(subBool -> subBool
                                .should(subShould -> subShould.matchPhrase(m -> m.field("content").query(content)))));
                    }
                    if (pdfContent != null && !pdfContent.isEmpty()) {
                        b.must(sb -> sb.bool(subBool -> subBool
                                .should(subShould -> subShould.matchPhrase(m -> m.field("content_sr").query(pdfContent)))
                                .should(subShould -> subShould.matchPhrase(m -> m.field("content_en").query(pdfContent)))
                        ));
                    }
                    return b;
                }));
            } else {
                q.must(mb -> mb.bool(b -> {
                    if (title != null && !title.isEmpty()) {
                        b.should(sb -> sb.match(m -> m.field("title").query(title).analyzer("serbian_simple")));
                    }
                    if (content != null && !content.isEmpty()) {
                        b.should(sb -> sb.match(m -> m.field("content").query(content).analyzer("serbian_simple")));
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


    private Query searchPostsByNumberOfComments(Integer minComments, Integer maxComments) {
        return RangeQuery.of(q -> {
            if (minComments != null) {
                q.field("total_comments").gte(JsonData.of(minComments));
            }
            if (maxComments != null) {
                q.field("total_comments").lte(JsonData.of(maxComments));
            }
            return q;
        })._toQuery();
    }
    public Query searchByNumOfLikesRange(Integer minLikes, Integer maxLikes) {
        return RangeQuery.of(q -> {
            if (minLikes != null) {
                q.field("total_likes").gte(JsonData.of(minLikes));
            }
            if (maxLikes != null) {
                q.field("total_likes").lte(JsonData.of(maxLikes));
            }
            return q;
        })._toQuery();
    }

    private List<PostDocument> runQuery(NativeQuery searchQuery) {
        SearchHits<PostDocument> searchHits = elasticsearchTemplate.search(searchQuery, PostDocument.class,
                IndexCoordinates.of("posts"));
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    private Query simpleSearchForTitle(String title) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("title").query(title).analyzer("serbian_simple")));
            return b;
        })))._toQuery();
    }

    private Query simpleSearchForContent(String content) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("content").query(content).analyzer("serbian_simple")));
            return b;
        })))._toQuery();
    }

    private Query simpleSearchForPDFContent(String content) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("content_sr").query(content).analyzer("serbian_simple")));
            b.should(sb -> sb.match(m -> m.field("content_en").query(content).analyzer("english")));
            return b;
        })))._toQuery();
    }

    private Query phraseSearchForTitle(String phrase) {
        return MatchPhraseQuery.of(q -> q.field("title").query(phrase).analyzer("serbian_simple"))._toQuery();
    }


    private Query phraseSearchForContent(String phrase) {
        return MatchPhraseQuery.of(q -> q.field("content").query(phrase).analyzer("serbian_simple"))._toQuery();
    }

    private Query phraseSearchForPDFContent(String phrase) {
        return MatchPhraseQuery.of(q -> q.field("content_sr").query(phrase).analyzer("serbian_simple"))._toQuery();
    }

    private Query fuzzySearchForTitle(String title) {
        return MatchQuery.of(q -> q.field("title").query(title).fuzziness(Fuzziness.ONE.asString()).analyzer("serbian_simple"))._toQuery();
    }

    private Query fuzzySearchForContent(String content) {
        return MatchQuery.of(q -> q.field("content").query(content).fuzziness(Fuzziness.ONE.asString()).analyzer("serbian_simple"))._toQuery();
    }


}
