package com.example.yoloq.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroupDTO {
    private Integer id;
    private String name;
    private String description;
    private String creationDate;
    private Set<UserDTO> users;
}
