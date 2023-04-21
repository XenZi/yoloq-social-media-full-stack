package com.example.yoloq.service;

import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.UserLoginDTO;
import com.example.yoloq.models.dto.UserRegisterDTO;

public interface UserService {
    UserDTO save(UserRegisterDTO newUser);
    User findByUsername(String username);
    UserDTO login(String username);
}
