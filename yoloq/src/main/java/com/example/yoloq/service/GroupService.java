package com.example.yoloq.service;

import com.example.yoloq.models.Group;
import com.example.yoloq.models.dto.GroupAdminDTO;
import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.models.dto.GroupRequestDTO;
import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.models.dto.requests.GroupJoinDecisionDTO;
import com.example.yoloq.models.dto.requests.SuspendGroupDTO;

import java.util.List;
import java.util.Set;

public interface GroupService {
    List<GroupDTO> findAll();
    GroupDTO save(GroupDTO newGroup);
    GroupDTO findById(int id);
    Group findEntityById(int id);
    GroupDTO update(GroupDTO updatedGroup);
    GroupDTO delete(int id);
    GroupAdminDTO createNewAdmin(int groupID, int userID);
    GroupAdminDTO deleteAdmin(int id);
    GroupRequestDTO joinGroup(int id);
    Set<GroupRequestDTO> getAllPendingMembersForGroup(int id);
    GroupRequestDTO updateGroupJoinRequest(GroupJoinDecisionDTO groupJoinDTO);
    Set<GroupRequestDTO> findAllMembersForGroup(int id);
    GroupDTO suspendGroup(SuspendGroupDTO suspendGroupDTO);
    Set<GroupDTO> findAllGroupsForUser(int id);

}
