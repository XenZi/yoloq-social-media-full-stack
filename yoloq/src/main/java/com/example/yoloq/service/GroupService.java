package com.example.yoloq.service;

import com.example.yoloq.models.Group;
import com.example.yoloq.models.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    List<GroupDTO> findAll();
    GroupDTO save(GroupDTO newGroup);
    GroupDTO update(GroupDTO updatedGroup);
    GroupDTO delete(int id);
}
