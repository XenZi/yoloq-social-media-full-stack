package com.example.yoloq.models.dto.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPostsBasedOnNumberOfCommentsDTO {
    private Integer greaterThan;
    private Integer lessThan;
}
