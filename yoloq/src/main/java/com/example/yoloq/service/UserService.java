package com.example.yoloq.service;

import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.models.dto.requests.UpdatePasswordDTO;

public interface UserService {
    UserDTO save(RegisterRequestDTO newUser);
    User findByUsername(String username);
    UserDTO login(String username);
    boolean isTheSamePassword(String enteredPassword, String userPassword);
    String updatePassword(UpdatePasswordDTO updatePasswordDTO, String token);
    User findByEmail(String email);
    User findLoggedUser();

}
