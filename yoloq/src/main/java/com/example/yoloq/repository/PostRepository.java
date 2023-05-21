package com.example.yoloq.repository;


import com.example.yoloq.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.imagePaths")
    List<Post> findAllWithComments();
    Optional<Post> findFirstById(int id);
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.imagePaths LEFT JOIN FETCH p.reactions WHERE p.id = ?1")
    Optional<Post> findFirstByIdWithCollections(int id);
}