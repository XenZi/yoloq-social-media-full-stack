package com.example.yoloq.service.impl;

import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_models.PostDocument;
import com.example.yoloq.elastic_repository.GroupDocumentRepository;
import com.example.yoloq.elastic_repository.PostDocumentRepository;
import com.example.yoloq.models.Comment;
import com.example.yoloq.models.Post;
import com.example.yoloq.models.Reaction;
import com.example.yoloq.models.dto.ReactionDTO;
import com.example.yoloq.repository.ReactionRepository;
import com.example.yoloq.service.CommentService;
import com.example.yoloq.service.PostService;
import com.example.yoloq.service.ReactionService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final PostDocumentRepository postDocumentRepository;
    private final GroupDocumentRepository groupDocumentRepository;
    @Autowired
    public ReactionServiceImpl(ReactionRepository reactionRepository,
                               CommentService commentService,
                               UserService userService,
                               PostService postService, ModelMapper modelMapper, PostDocumentRepository postDocumentRepository, GroupDocumentRepository groupDocumentRepository) {
        this.reactionRepository = reactionRepository;
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.postDocumentRepository = postDocumentRepository;
        this.groupDocumentRepository = groupDocumentRepository;
    }

    @Override
    public ReactionDTO create(ReactionDTO reactionDTO) {
        Reaction reaction = modelMapper.map(reactionDTO, Reaction.class);
        reaction.setTimestamp(LocalDateTime.now());
        reaction.setReactedBy(userService.findLoggedUser());
        reaction.setType(reactionDTO.getType());
        if (reactionDTO.getCommentIdReactedTo() != null) {
            Comment comment = commentService.findOneEntity( reactionDTO.getCommentIdReactedTo());
            reaction.setReactedTo(comment);
            if (comment.getPost() != null) {
                Post post = comment.getPost();
                PostDocument postDocument = postDocumentRepository.findByDatabaseId(post.getId()).orElse(null);
                if (postDocument != null) {
                    postDocument.setTotalLikes(postDocument.getTotalLikes() + 1);
                    this.postDocumentRepository.save(postDocument);
                }
            }
        }
        if (reactionDTO.getPostIdReactedTo() != null) {
            Post post = postService.findOnePost(reactionDTO.getPostIdReactedTo());
            reaction.setPostReactedTo(post);
            PostDocument postDocument = postDocumentRepository.findByDatabaseId(post.getId()).orElse(null);
            if (postDocument != null) {
                postDocument.setTotalLikes(postDocument.getTotalLikes() + 1);
                this.postDocumentRepository.save(postDocument);
                if (postDocument.getGroupID() != null) {
                    GroupDocument groupDocument = groupDocumentRepository.findByDatabaseId(postDocument.getGroupID()).orElse(null);
                    System.out.println(postDocument.getGroupID());
                    if (groupDocument != null) {
                        groupDocument.setTotalLikes(groupDocument.getTotalLikes() + 1);
                        groupDocument.setAvgNumberOfLikes((float) (groupDocument.getTotalLikes() / groupDocument.getNumPosts()));
                        this.groupDocumentRepository.save(groupDocument);
                    }
                }
            }
        }
        reaction = reactionRepository.save(reaction);
        return modelMapper.map(reaction, ReactionDTO.class);
    }

    @Override
    public ReactionDTO delete(int id) {
        Reaction reaction = this.reactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reaction not found"));
        reaction.setDeleted(true);
        reactionRepository.save(reaction);
        return modelMapper.map(reaction, ReactionDTO.class);
    }

    @Override
    public ReactionDTO update(ReactionDTO updatedReaction) {
        Reaction reaction = this.reactionRepository.findById(updatedReaction.getId()).orElseThrow(() -> new EntityNotFoundException("Reaction not found"));
        reaction.setType(updatedReaction.getType());
        reaction.setTimestamp(LocalDateTime.now());
        reactionRepository.save(reaction);
        return modelMapper.map(reaction, ReactionDTO.class);
    }

    @Override
    public Set<ReactionDTO> findAllForComment(int id) {
        return this.reactionRepository.findByCommentId(id).stream().map(reaction -> {
            return modelMapper.map(reaction, ReactionDTO.class);
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<ReactionDTO> findAllForPost(int id) {
        return this.reactionRepository.findByPostId(id).stream().map(reaction -> modelMapper.map(reaction, ReactionDTO.class)).collect(Collectors.toSet());
    }
}
