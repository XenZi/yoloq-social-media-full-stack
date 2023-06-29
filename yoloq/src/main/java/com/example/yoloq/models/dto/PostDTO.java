package com.example.yoloq.models.dto;


import com.example.yoloq.models.Comment;
import com.example.yoloq.models.Group;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PostDTO {
    private Integer id;
    private String content;
    private String creationDate;
    private Set<String> imagePaths = new HashSet<>();
    private UserDTO postedBy;
    private GroupDTO postedInGroup;
    private Integer postedInGroupID;
}
