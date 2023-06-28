package com.example.yoloq.controller;

import com.example.yoloq.models.dto.GroupAdminDTO;
import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.models.dto.GroupRequestDTO;
import com.example.yoloq.models.dto.requests.GroupJoinDecisionDTO;
import com.example.yoloq.models.dto.requests.CreateAdminDTO;
import com.example.yoloq.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupDTO> save(@RequestBody GroupDTO groupDTO) {
        return new ResponseEntity<>(this.groupService.save(groupDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAll() {
        return new ResponseEntity<>(this.groupService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> update(@RequestBody GroupDTO groupDTO) {
        return new ResponseEntity<>(this.groupService.update(groupDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GroupDTO> delete(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<GroupAdminDTO> createNewAdmin(@RequestBody CreateAdminDTO createAdminDTO) {
        return new ResponseEntity<>(this.groupService.createNewAdmin(createAdminDTO.getGroupID(), createAdminDTO.getUserID()), HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<GroupAdminDTO> deleteAdmin(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.deleteAdmin(id), HttpStatus.OK);
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<GroupRequestDTO> joinGroup(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.joinGroup(id), HttpStatus.OK);
    }

    @GetMapping("/members/pending/{id}")
    public ResponseEntity<Set<GroupRequestDTO>> getAllPendingMembersForGroup(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.getAllPendingMembersForGroup(id), HttpStatus.OK);
    }

    @PostMapping("/members/update-join-request")
    public ResponseEntity<GroupRequestDTO> approveMemberJoin(@RequestBody GroupJoinDecisionDTO approveGroupJoinDTO) {
        return new ResponseEntity<>(this.groupService.updateGroupJoinRequest(approveGroupJoinDTO), HttpStatus.OK);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Set<GroupRequestDTO>> findAllMembersForGroup(@PathVariable Integer id) {
        return new ResponseEntity<>(this.groupService.findAllMembersForGroup(id), HttpStatus.OK);
    }
}
