package com.example.yoloq.service;

import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;

public interface UserService {
    UserDTO save(RegisterRequestDTO newUser);
    User findByUsername(String username);
    UserDTO login(String username);
}
