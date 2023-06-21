package com.example.yoloq.repository;

import com.example.yoloq.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.post LEFT JOIN FETCH c.reactions LEFT JOIN FETCH c.replies LEFT JOIN FETCH c.reports WHERE c.id = ?1 AND c.isDeleted = false")
    Optional<Comment> findByIdWithCollections(int id);

    @Query("SELECT c FROM Comment c LEFT JOIN c.post LEFT JOIN FETCH c.reactions LEFT JOIN FETCH c.replies LEFT JOIN FETCH c.reports WHERE c.post.id = ?1 AND c.isDeleted = false")
    List<Comment> findAllCommentsByPost(int id);
}
