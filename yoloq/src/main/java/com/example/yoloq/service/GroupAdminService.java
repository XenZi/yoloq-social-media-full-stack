package com.example.yoloq.service;

import com.example.yoloq.models.Group;
import com.example.yoloq.models.GroupAdmin;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.GroupAdminDTO;

import java.util.Set;

public interface GroupAdminService {
    GroupAdmin createEntity(Group group);
    GroupAdmin createEntity(Group group, User user);
    GroupAdmin delete(int id);
    Set<GroupAdminDTO> findAllByGroupID(Integer id);
}
