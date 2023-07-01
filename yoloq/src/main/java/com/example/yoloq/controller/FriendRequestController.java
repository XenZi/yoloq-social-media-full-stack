package com.example.yoloq.controller;


import com.example.yoloq.models.dto.FriendRequestDTO;
import com.example.yoloq.models.dto.requests.UpdateFriendRequestDTO;
import com.example.yoloq.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/friend-request")
public class FriendRequestController {

    private final FriendRequestService service;

    @Autowired
    public FriendRequestController(FriendRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FriendRequestDTO> create(@RequestBody FriendRequestDTO friendRequestDTO) {
        return new ResponseEntity<>(this.service.save(friendRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<Set<FriendRequestDTO>> findAllPendingForUser() {
        return new ResponseEntity<>(this.service.findAllPendingForUser(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FriendRequestDTO> update(@RequestBody UpdateFriendRequestDTO update) {
        return new ResponseEntity<>(this.service.update(update), HttpStatus.OK);
    }
}
