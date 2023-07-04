package com.example.yoloq.repository;

import com.example.yoloq.models.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    @Query("SELECT DISTINCT f FROM FriendRequest f WHERE f.userTo.id = ?1 AND f.approved is NULL")
    List<FriendRequest> findAllByPendingAndUserTo(int id);

    @Query("SELECT DISTINCT f FROM FriendRequest f LEFT JOIN f.userTo LEFT JOIN f.userFrom WHERE f.id = ?1")
    Optional<FriendRequest> findById(int id);

}
