package com.example.yoloq.repository;

import com.example.yoloq.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Override
    Optional<Group> findById(Integer integer);

    @Override
    @Query("SELECT DISTINCT g FROM Group g WHERE g.isDeleted = false AND g.isSuspended = false")
    List<Group> findAll();

}
