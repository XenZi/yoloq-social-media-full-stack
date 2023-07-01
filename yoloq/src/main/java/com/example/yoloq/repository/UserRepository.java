package com.example.yoloq.repository;

import com.example.yoloq.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    Optional<User> findFirstByUsername(String username);
    Optional<User> findFirstByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN u.friends WHERE u.id = ?1")
    Optional<User> findByIdWithFriends(int id);


}
