package com.example.yoloq.mappers;

import com.example.yoloq.enums.Role;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class UserMapperFromRegisterDTO implements Function<UserRegisterDTO, User> {

    @Override
    public User apply(UserRegisterDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setEmail(userDTO.email());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setProfileImagePath("");
        user.setRole(Role.USER);
        return user;
    }
}
