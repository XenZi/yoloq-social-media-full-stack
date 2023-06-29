package com.example.yoloq.service;

import com.example.yoloq.models.Group;
import com.example.yoloq.models.GroupRequest;
import com.example.yoloq.models.dto.GroupRequestDTO;

import java.util.Set;

public interface GroupRequestService {

    GroupRequestDTO save(Group group);
    GroupRequestDTO saveForUser(Group group, int userID);
    Set<GroupRequestDTO> findAllMembersByGroupID(int id);
    GroupRequestDTO update(boolean updateStatus, int groupRequestID);
    Set<GroupRequestDTO> findAllRequestsOnPendingByGroupID(int id);
    Set<GroupRequestDTO> findAllGroupsWhereUser(int id);

}
