package com.example.yoloq.models.dto;

import com.example.yoloq.models.GroupAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupAdminDTO {
    private Integer id;
    private UserDTO user;
    private Integer groupID;

}
