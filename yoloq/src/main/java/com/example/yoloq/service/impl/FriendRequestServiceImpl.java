package com.example.yoloq.service.impl;

import com.example.yoloq.models.FriendRequest;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.FriendRequestDTO;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.UpdateFriendRequestDTO;
import com.example.yoloq.repository.FriendRequestRepository;
import com.example.yoloq.service.FriendRequestService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository, UserService userService, ModelMapper modelMapper) {
        this.friendRequestRepository = friendRequestRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public FriendRequestDTO save(FriendRequestDTO dto) {
        FriendRequest friendRequest = new FriendRequest();
        User user = this.userService.findLoggedUser();
        User to = this.userService.findById(dto.getUserToID());
        friendRequest.setApproved(null);
        friendRequest.setUserFrom(user);
        friendRequest.setUserTo(to);
        friendRequest.setCreatedAt(LocalDateTime.now());
        friendRequest = friendRequestRepository.save(friendRequest);
        return mapToDTO(friendRequest);
    }

    @Override
    public Set<FriendRequestDTO> findAllPendingForUser() {
        return new HashSet<>(this.friendRequestRepository
                .findAllByPendingAndUserTo(this.userService.findLoggedUser().getId())
                .stream()
                .collect(Collectors.toMap(
                        FriendRequest::getId,
                        this::mapToDTO,
                        (existingValue, newValue) -> existingValue,
                        HashMap::new
                ))
                .values());
    }

    @Override
    public FriendRequestDTO update(UpdateFriendRequestDTO updateFriendRequestDTO) {
        FriendRequest friendRequest = this.friendRequestRepository.findById(updateFriendRequestDTO.getRequestID()).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        friendRequest.setAt(LocalDateTime.now());
        friendRequest.setApproved(updateFriendRequestDTO.isDecision());
        friendRequest = friendRequestRepository.save(friendRequest);
        if (updateFriendRequestDTO.isDecision()) {
            this.userService.updateFriendships(friendRequest.getUserFrom().getId(), friendRequest.getUserTo().getId());
        }
        return mapToDTO(friendRequest);
    }

    @Override
    public Set<FriendRequestDTO> findAllAcceptedForUser() {
        return null;
    }

    private FriendRequestDTO mapToDTO(FriendRequest friendRequest) {
        FriendRequestDTO returnableDTO = modelMapper.map(friendRequest, FriendRequestDTO.class);
        returnableDTO.setUserFrom(modelMapper.map(friendRequest.getUserFrom(), UserDTO.class));
        returnableDTO.setUserTo(modelMapper.map(friendRequest.getUserTo(), UserDTO.class));
        return returnableDTO;
    }
}
