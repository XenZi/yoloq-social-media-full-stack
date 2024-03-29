package com.example.yoloq.service;

import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.models.dto.requests.UpdatePasswordDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface UserService {
    UserDTO save(RegisterRequestDTO newUser, MultipartFile profileImage);
    User findByUsername(String username);
    UserDTO findUserById(int id);
    UserDTO login(String username);
    String updatePassword(UpdatePasswordDTO updatePasswordDTO);
    User findByEmail(String email);
    User findLoggedUser();
    UserDTO findLoggedUserForDTOResponse();
    User findById(int id);
    UserDTO updateDetails(UserDTO userDTO);
    Set<UserDTO> updateFriendships(Integer userId, Integer friendId);
    Set<UserDTO> getAllFriendsForUser(int id);
    UserDTO removeFriend(int friendID);
    Set<UserDTO> searchUsers(String firstName, String lastName);
    UserDTO updateProfileImage(MultipartFile file);
}

