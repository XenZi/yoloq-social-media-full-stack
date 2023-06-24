package com.example.yoloq.models.dto;

import com.example.yoloq.enums.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDTO {
    private Integer id;
    private ReactionType type;
    private String timestamp;
    private Integer commentIdReactedTo;
    private Integer postIdReactedTo;
    private UserDTO reactedBy;
}
