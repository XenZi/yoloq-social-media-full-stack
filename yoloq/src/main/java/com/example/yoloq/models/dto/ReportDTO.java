package com.example.yoloq.models.dto;


import com.example.yoloq.enums.ReportReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Integer id;
    private ReportReason reportReason;
    private String timestamp;
    private Integer commentID;
    private Integer postID;
    private PostDTO reportedPost;
    private UserDTO byUser;
    private CommentDTO reportedComment;
}
