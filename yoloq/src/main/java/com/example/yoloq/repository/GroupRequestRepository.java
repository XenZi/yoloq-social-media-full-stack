package com.example.yoloq.repository;

import com.example.yoloq.models.Group;
import com.example.yoloq.models.GroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRequestRepository extends JpaRepository<GroupRequest, Integer> {

    @Query("SELECT DISTINCT r FROM GroupRequest r WHERE r.requestFrom.id = ?1 AND r.forGroup.id = ?2")
    Optional<GroupRequest> findFirstByUserIDAndGroupID(int userID, int groupID);

    @Query("SELECT DISTINCT r FROM GroupRequest r WHERE r.approved IS NULL AND r.forGroup.id = ?1")
    List<GroupRequest> findAllByPendingAndGroupID(int groupID);

    @Query("SELECT DISTINCT r FROM GroupRequest r WHERE r.id = ?1")
    Optional<GroupRequest> findFirstById(int id);

    @Query("SELECT DISTINCT r FROM GroupRequest r WHERE r.approved = true AND r.forGroup.id = ?1")
    List<GroupRequest> findAllByApprovedAndGroupID(int groupID);
}
