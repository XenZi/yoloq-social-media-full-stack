package com.example.yoloq.repository;


import com.example.yoloq.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.images WHERE p.isDeleted = false ")
    List<Post> findAllWithComments();
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.images WHERE p.isDeleted = false AND p.id = ?1")
    Optional<Post> findFirstByIdWithCollections(int id);
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.images WHERE p.isDeleted = false AND p.postedInGroup.id = ?1")
    List<Post> findAllByGroupID(int id);
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN p.comments LEFT JOIN FETCH p.images WHERE p.isDeleted = false AND p.postedBy.id = ?1")
    List<Post> findAllByUserID(int id);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.images WHERE p.isDeleted = false ORDER BY p.creationDate ASC")
    List<Post> findAllByCreationDateAscending();

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.images WHERE p.isDeleted = false ORDER BY p.creationDate DESC")
    List<Post> findAllByCreationDateDescending();

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH p.images WHERE p.postedBy.id IN (:friendsIds) AND " +
            "(p.postedInGroup.id IN (:groupIds) OR p.postedInGroup.id IS NULL) AND " +
            "p.isDeleted = false AND (p.postedInGroup IS NULL OR EXISTS " +
            "(SELECT gr FROM GroupRequest gr WHERE gr.forGroup.id = p.postedInGroup.id AND gr.requestFrom.id = :userId AND gr.approved = true))")
    Set<Post> findPostsByFriendsAndGroups(
            @Param("friendsIds") Set<Integer> friendsIds,
            @Param("groupIds") Set<Integer> groupIds,
            @Param("userId") Integer userId);

}
