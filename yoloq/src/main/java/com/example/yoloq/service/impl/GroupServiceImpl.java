package com.example.yoloq.service.impl;

import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.Group;
import com.example.yoloq.models.GroupAdmin;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.GroupAdminDTO;
import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.repository.GroupRepository;
import com.example.yoloq.service.GroupAdminService;
import com.example.yoloq.service.GroupService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final GroupAdminService groupAdminService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper, UserService userService, GroupAdminService groupAdminService) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.groupAdminService = groupAdminService;
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
        return modelMapper.map(admin, GroupAdminDTO.class);
    }

    @Override
    public GroupAdminDTO deleteAdmin(int id) {
        return modelMapper.map(this.groupAdminService.delete(id), GroupAdminDTO.class);
    }

    private Group findOneById(int id) {
        Optional<Group> group = this.groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }
        return group.get();
    }
}
