package com.example.yoloq.repository;

import com.example.yoloq.models.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {


    @Query("SELECT r FROM Reaction r WHERE r.postReactedTo.id = ?1 AND r.isDeleted = false")
    List<Reaction> findByPostId(int id);

    @Query("SELECT r FROM Reaction r WHERE r.reactedTo.id = ?1 AND r.isDeleted = false")
    List<Reaction> findByCommentId(int id);
}
