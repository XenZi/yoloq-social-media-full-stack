package com.example.yoloq.models.dto;

import com.example.yoloq.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private Role role;
}
