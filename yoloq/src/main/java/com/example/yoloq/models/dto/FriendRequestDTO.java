package com.example.yoloq.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDTO {
    private Integer id;
    private UserDTO userFrom;
    private UserDTO userTo;
    private boolean approved;
    private String createdAt;
    private String at;
    private Integer userToID;
}
