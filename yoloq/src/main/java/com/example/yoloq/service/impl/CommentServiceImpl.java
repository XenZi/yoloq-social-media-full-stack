package com.example.yoloq.service.impl;

import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.Comment;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.dto.CommentDTO;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.repository.CommentRepository;
import com.example.yoloq.service.CommentService;
import com.example.yoloq.service.PostService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, UserService userService, PostService postService) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public CommentDTO save(CommentDTO newComment) {
        Comment comment = convertToEntity(newComment);
        setAdditionalAttributes(comment, newComment);
        comment = commentRepository.save(comment);
        CommentDTO commentDTO = convertToDTO(comment);
        setAdditionalDTOAttributes(commentDTO, newComment);
        return commentDTO;
    }

    @Override
    public CommentDTO findOne(Integer id) {
        Comment comment = commentRepository.findByIdWithCollections(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        CommentDTO commentDto = convertToDTO(comment);
        commentDto.setCommentReplies(getReplies(comment.getReplies()));
        return commentDto;
    }

    @Override
    public Set<CommentDTO> mapCommentsToSet(Set<Comment> comments) {
        return comments.stream().map(this::convertToDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<CommentDTO> listAllForPost(Integer postID) {
        return commentRepository.findAllCommentsByPost(postID).stream().map(comment -> {
            CommentDTO commentDTO = convertToDTO(comment);
            commentDTO.setCommentReplies(getReplies(comment.getReplies()));
            return commentDTO;
        }).collect(Collectors.toSet());
    }

    private Set<CommentDTO> getReplies(Set<Comment> comments) {
        return comments.stream().filter(comment -> !comment.isDeleted()).map(reply -> {
            CommentDTO replyDto = convertToDTO(reply);
            reply = this.commentRepository.findByIdWithCollections(reply.getId()).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
            replyDto.setCommentReplies(getReplies(reply.getReplies()));
            return replyDto;
        }).collect(Collectors.toSet());
    }

    @Override
    public CommentDTO update(CommentDTO updatedComment) {
        Comment comment = this.commentRepository.findByIdWithCollections(updatedComment.getId()).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setText(updatedComment.getText());
        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public CommentDTO delete(Integer id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setDeleted(true);
        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentDTO.class);
    }


    @Override
    public Comment findOneEntity(Integer id) {
        return this.commentRepository.findByIdWithCollections(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }

    @Override
    public Set<CommentDTO> findAllRepliesForComment(Integer id) {
        Comment comment = this.findOneEntity(id);
        return getReplies(comment.getReplies());
    }

    private void setAdditionalAttributes(Comment comment, CommentDTO newComment) {
        comment.setUser(userService.findLoggedUser());
        comment.setCreatedAt(LocalDateTime.now());
        if (newComment.getParentCommentID() != null) {
            comment.setParent(commentRepository.findById(newComment.getParentCommentID()).orElseThrow(() -> new ResourceNotFoundException("Error while finding parent comment.")));
        }
        if (newComment.getPostId() != null) {
            Post post = postService.findOnePost(newComment.getPostId());
            comment.setPost(post);
            post.addComment(comment);
        }
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        if (commentDTO.getPostId() != null) {
            comment.setPost(modelMapper.map(this.postService.findOne(commentDTO.getPostId()), Post.class));
        }
        return comment;
    }

    private void setAdditionalDTOAttributes(CommentDTO commentDTO, CommentDTO newComment) {
        commentDTO.setPostedBy(userService.findLoggedUserForDTOResponse());
        if (newComment.getPostId() != null) {
            commentDTO.setPostId(newComment.getPostId());
        }
    }

    CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        if (comment.getParent() != null) {
            commentDTO.setParentCommentID(comment.getParent().getId());
        }
        if (comment.getPost() != null) {
            commentDTO.setPostId(comment.getPost().getId());
        }
        commentDTO.setPostedBy(modelMapper.map(comment.getUser(), UserDTO.class));
        return commentDTO;
    }
}
