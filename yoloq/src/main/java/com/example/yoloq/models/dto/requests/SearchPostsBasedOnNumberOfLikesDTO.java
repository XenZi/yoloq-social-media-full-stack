package com.example.yoloq.models.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchPostsBasedOnNumberOfLikesDTO {
    Integer greaterThan;
    Integer lessThan;
}
