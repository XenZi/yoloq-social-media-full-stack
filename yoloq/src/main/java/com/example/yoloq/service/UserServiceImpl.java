package com.example.yoloq.service;

import com.example.yoloq.contracts.service.UserService;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.User;
import com.example.yoloq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Searching user is not found");
        }
        return user.get();
    }
}
