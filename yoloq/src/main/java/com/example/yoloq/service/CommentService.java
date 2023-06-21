package com.example.yoloq.service;

import com.example.yoloq.models.Comment;
import com.example.yoloq.models.dto.CommentDTO;

import java.util.Set;

public interface CommentService {
    CommentDTO save(CommentDTO newComment);
    CommentDTO findOne(Integer id);
    Set<CommentDTO> mapCommentsToSet(Set<Comment> comments);
    Set<CommentDTO> listAllForPost(Integer postID);
    CommentDTO update(CommentDTO updatedComment);
    CommentDTO delete(Integer id);
}
