package com.example.yoloq.models.dto;

import com.example.yoloq.enums.Role;

public record UserDTO(
        Integer id,
        String username,
        String email,
        String firstName,
        String lastName,
        Role role
) {
}
