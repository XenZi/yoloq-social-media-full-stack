package com.example.yoloq.service;

import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.models.dto.requests.UpdatePasswordDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDTO save(RegisterRequestDTO newUser, MultipartFile profileImage);
    User findByUsername(String username);
    UserDTO login(String username);
    String updatePassword(UpdatePasswordDTO updatePasswordDTO);
    User findByEmail(String email);
    User findLoggedUser();
    UserDTO findLoggedUserForDTOResponse();
    User findById(int id);
}

