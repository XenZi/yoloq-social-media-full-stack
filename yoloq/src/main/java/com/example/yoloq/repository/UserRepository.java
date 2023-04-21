package com.example.yoloq.repository;

import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserRegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
    Optional<User> findFirstByUsername(String username);
}
