package com.example.yoloq.service.impl;

import com.example.yoloq.models.Group;
import com.example.yoloq.models.GroupRequest;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.models.dto.GroupRequestDTO;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.repository.GroupRequestRepository;
import com.example.yoloq.service.GroupRequestService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupRequestServiceImpl implements GroupRequestService {
    private final GroupRequestRepository groupRequestRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public GroupRequestServiceImpl(GroupRequestRepository groupRequestRepository, UserService userService, ModelMapper modelMapper) {
        this.groupRequestRepository = groupRequestRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public GroupRequestDTO save(Group group) {
        User user = this.userService.findLoggedUser();
        Optional<GroupRequest> doesRequestExist = this.groupRequestRepository.findFirstByUserIDAndGroupID(user.getId(), group.getId());
        if (doesRequestExist.isPresent()) {
            throw new RuntimeException("You are already part of this group");
        }
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setForGroup(group);
        groupRequest.setRequestFrom(user);
        groupRequest.setCreatedAt(LocalDateTime.now());
        groupRequest.setApproved(null);
        groupRequest = groupRequestRepository.save(groupRequest);
        return mapToDTO(groupRequest);
    }

    @Override
    public Set<GroupRequestDTO> findAllMembersByGroupID(int id) {
        return this.groupRequestRepository
                .findAllByApprovedAndGroupID(id)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public GroupRequestDTO update(boolean updateStatus, int groupRequestID) {
        GroupRequest groupRequest = this.groupRequestRepository.findFirstById(groupRequestID).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        groupRequest.setApproved(updateStatus);
        groupRequest.setAt(LocalDateTime.now());
        groupRequest = groupRequestRepository.save(groupRequest);
        return mapToDTO(groupRequest);
    }

    @Override
    public Set<GroupRequestDTO> findAllRequestsOnPendingByGroupID(int id) {
        return this.groupRequestRepository.findAllByPendingAndGroupID(id)
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toSet());
    }

    private GroupRequestDTO mapToDTO(GroupRequest groupRequest) {
        GroupRequestDTO dto = modelMapper.map(groupRequest, GroupRequestDTO.class);
        dto.setRequestFrom(modelMapper.map(groupRequest.getRequestFrom(), UserDTO.class));
        dto.setForGroup(modelMapper.map(groupRequest.getForGroup(), GroupDTO.class));
        return dto;
    }
}
