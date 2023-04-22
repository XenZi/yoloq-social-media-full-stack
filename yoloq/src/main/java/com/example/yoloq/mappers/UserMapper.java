package com.example.yoloq.mappers;

import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;

public class UserMapper {

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole());
    }

    public static User fromDTO()
}
