package com.example.yoloq.service.impl;

import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.Group;
import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.repository.GroupRepository;
import com.example.yoloq.service.GroupService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper, UserService userService) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
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
        groupRepository.save(group);
        return newGroup;
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

    private Group findOneById(int id) {
        Optional<Group> group = this.groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }
        return group.get();
    }
}
