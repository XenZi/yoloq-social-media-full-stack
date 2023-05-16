package com.example.yoloq.models.dto;


import com.example.yoloq.models.Comment;
import com.example.yoloq.models.Group;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class PostDTO {
    private Integer id;
    private String content;
    private LocalDateTime creationDate;
    private Set<String> imagePaths;
    private UserDTO postedBy;
    private Set<Comment> comments;
    private Group postedInGroup;
    private Set<MultipartFile> images;
}
