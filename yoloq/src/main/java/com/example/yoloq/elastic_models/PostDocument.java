package com.example.yoloq.elastic_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "posts")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class PostDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "title", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String title;

    @Field(type = FieldType.Text, store = true, name = "content", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String content;

    @Field(type = FieldType.Integer, store = true, name = "group_id")
    private Integer groupID;

    @Field(type = FieldType.Text, store = true, name = "content_sr", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String contentSr;

    @Field(type = FieldType.Text, store = true, name = "content_en", analyzer = "english", searchAnalyzer = "english")
    private String contentEn;

    @Field(type = FieldType.Text, store = true, name = "server_filename", index = false)
    private String serverFilename;

    @Field(type = FieldType.Integer, store = true, name = "database_id")
    private Integer databaseId;

    @Field(type = FieldType.Integer, store = true, name = "total_likes")
    private Integer totalLikes;

    @Field(type = FieldType.Integer, store = true, name = "total_comments")
    private Integer totalComments;
}
