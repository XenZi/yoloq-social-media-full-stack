package com.example.yoloq.repository;

import com.example.yoloq.models.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin, Integer> {

    @Query("SELECT DISTINCT g FROM GroupAdmin g WHERE g.adminAt.id = ?1 AND g.isDeleted = false")
    List<GroupAdmin> findAllByGroupID(Integer id);

    @Query("SELECT DISTINCT g FROM GroupAdmin g WHERE g.id = ?1")
    Optional<GroupAdmin> findById(int id);

    @Query("SELECT DISTINCT g FROM GroupAdmin g WHERE g.adminAt.id = ?1 AND g.user.id = ?2")
    Optional<GroupAdmin> findByUserIDAndGroupID(int groupID, int userID);
}
