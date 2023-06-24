package com.example.yoloq.service;

import com.example.yoloq.models.dto.ReactionDTO;

import java.util.Set;

public interface ReactionService {
    ReactionDTO create(ReactionDTO reactionDTO);
    ReactionDTO delete(int id);
    ReactionDTO update(ReactionDTO updatedReaction);
    Set<ReactionDTO> findAllForComment(int id);
    Set<ReactionDTO> findAllForPost(int id);
}
