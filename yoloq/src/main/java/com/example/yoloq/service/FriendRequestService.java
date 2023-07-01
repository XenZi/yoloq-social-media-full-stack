package com.example.yoloq.service;

import com.example.yoloq.models.dto.FriendRequestDTO;
import com.example.yoloq.models.dto.requests.UpdateFriendRequestDTO;

import java.util.Set;

public interface FriendRequestService {
    FriendRequestDTO save(FriendRequestDTO dto);
    Set<FriendRequestDTO> findAllPendingForUser();
    FriendRequestDTO update(UpdateFriendRequestDTO updateFriendRequestDTO);
}
