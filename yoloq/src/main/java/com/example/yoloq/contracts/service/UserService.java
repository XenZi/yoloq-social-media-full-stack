package com.example.yoloq.contracts.service;

import com.example.yoloq.models.User;

public interface UserService {
    User findByUsername(String username);
}
