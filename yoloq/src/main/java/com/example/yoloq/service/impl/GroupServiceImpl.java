package com.example.yoloq.service.impl;

import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.exception.UnauthorizedAccessException;
import com.example.yoloq.models.Group;
import com.example.yoloq.models.GroupAdmin;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.GroupAdminDTO;
import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.models.dto.GroupRequestDTO;
import com.example.yoloq.models.dto.requests.GroupJoinDecisionDTO;
import com.example.yoloq.models.dto.requests.SuspendGroupDTO;
import com.example.yoloq.repository.GroupRepository;
import com.example.yoloq.service.GroupAdminService;
import com.example.yoloq.service.GroupRequestService;
import com.example.yoloq.service.GroupService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final GroupAdminService groupAdminService;
    private final GroupRequestService groupRequestService;
    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper, UserService userService, GroupAdminService groupAdminService, GroupRequestService groupRequestService) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.groupAdminService = groupAdminService;
        this.groupRequestService = groupRequestService;
    }


    @Override
    public List<GroupDTO> findAll() {
        List<GroupDTO> allGroupsDTOs = new ArrayList<>();
        List<Group> allGroups = this.groupRepository.findAll();
        for (Group group :
                allGroups) {
            allGroupsDTOs.add(modelMapper.map(group, GroupDTO.class));
        }
        return allGroupsDTOs;
    }

    @Override
    public GroupDTO save(GroupDTO newGroup) {
        Group group = new Group();
        group.setCreationDate(LocalDateTime.now());
        group.setDeleted(false);
        group.setName(newGroup.getName());
        group.setDescription(newGroup.getDescription());
        group = groupRepository.save(group);
        group.getAdmins().add(groupAdminService.createEntity(group));
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupDTO findById(int id) {
        return modelMapper.map(this.groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found")), GroupDTO.class);
    }

    @Override
    public Group findEntityById(int id) {
        return this.findOneById(id);
    }

    @Override
    public GroupDTO update(GroupDTO updatedGroup) {
        Group group = this.findOneById(updatedGroup.getId());
        group.setName(updatedGroup.getName());
        group.setDescription(updatedGroup.getDescription());
        group = groupRepository.save(group);
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupDTO delete(int id) {
        Group group = this.findOneById(id);
        group.setDeleted(true);
        group = groupRepository.save(group);

        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupAdminDTO createNewAdmin(int groupID, int userID) {
        User user = this.userService.findById(userID);
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        GroupAdmin admin = this.groupAdminService.createEntity(group, user);
        logger.info("New admin with id " + admin.getId() + " created, for group with the id " + groupID);
        return modelMapper.map(admin, GroupAdminDTO.class);
    }

    @Override
    public GroupAdminDTO deleteAdmin(int id) {
        GroupAdmin groupAdmin = this.groupAdminService.delete(id);
        Group group = this.findOneById(groupAdmin.getAdminAt().getId());
        GroupRequestDTO request = this.groupRequestService.saveForUser(group, groupAdmin.getUser().getId());
        this.groupRequestService.update(true, request.getId());
        logger.info("Admin with id " + groupAdmin.getId() + " deleted.");
        return modelMapper.map(this.groupAdminService.delete(id), GroupAdminDTO.class);
    }

    @Override
    public GroupRequestDTO joinGroup(int id) {
        Group group = this.groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Group you were looking for is not found"));
        return this.groupRequestService.save(group);
    }

    @Override
    public Set<GroupRequestDTO> getAllPendingMembersForGroup(int id) {
        Group group = this.findOneById(id);
        if (!this.groupAdminService.isLoggedUserAdminInGroup(id)) {
            throw new UnauthorizedAccessException("You're not admin! Permission denied");
        }

        return this.groupRequestService.findAllRequestsOnPendingByGroupID(id);
    }

    @Override
    public GroupRequestDTO updateGroupJoinRequest(GroupJoinDecisionDTO groupJoinDTO) {
        if (!this.groupAdminService.isLoggedUserAdminInGroup(groupJoinDTO.getGroupID())) {
            throw new UnauthorizedAccessException("You're not admin! Permission denied");
        }
        return this.groupRequestService.update(groupJoinDTO.isDecision(), groupJoinDTO.getRequestID());
    }

    @Override
    public Set<GroupRequestDTO> findAllMembersForGroup(int id) {
        return this.groupRequestService.findAllMembersByGroupID(id);
    }

    @Override
    public GroupDTO suspendGroup(SuspendGroupDTO suspendGroupDTO) {
        Group group = this.findOneById(suspendGroupDTO.getGroupID());
        group.setSuspended(true);
        group.setSuspendedReason(suspendGroupDTO.getSuspendedReason());
        groupAdminService.removeAllAdminsByGroupID(group.getId());
        group = groupRepository.save(group);
        logger.info("Admin with the id " + this.userService.findLoggedUser().getId() + " just suspended the group with the id " + group.getId() + ". ");
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public Set<GroupDTO> findAllGroupsForUser(int id) {
        List<GroupAdminDTO> allAdminPositions = this.groupAdminService.findAllWhereUserIsAdmin(id);
        List<GroupDTO> groupDTOS = allAdminPositions.stream().map(groupAdminDTO -> {
            Group group = this.findEntityById(groupAdminDTO.getGroupID());
            return modelMapper.map(group, GroupDTO.class);
        }).toList();

        List<GroupDTO> secondList = this.groupRequestService.findAllGroupsWhereUser(id).stream().map(request -> {
            Group group = this.findOneById(request.getForGroup().getId());
            return modelMapper.map(group, GroupDTO.class);
        }).toList();

        List<GroupDTO> joinedList = new ArrayList<>();
        joinedList.addAll(secondList);
        joinedList.addAll(groupDTOS);
        return new HashSet<>(joinedList);
    }

    @Override
    public boolean isUserAdminOfGroup(int groupID) {
        List<GroupAdminDTO> allAdminPositions = this.groupAdminService.findAllWhereUserIsAdmin(this.userService.findLoggedUser().getId());
        List<GroupDTO> groupDTOS = allAdminPositions.stream().map(groupAdminDTO -> {
            Group group = this.findEntityById(groupAdminDTO.getGroupID());
            return modelMapper.map(group, GroupDTO.class);
        }).toList();
        Optional<GroupDTO> groupOptional = groupDTOS.stream()
                .filter(groupDTO -> groupDTO.getId() == groupID)
                .findFirst();

        if (groupOptional.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private Group findOneById(int id) {
        Optional<Group> group = this.groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }
        return group.get();
    }
}
