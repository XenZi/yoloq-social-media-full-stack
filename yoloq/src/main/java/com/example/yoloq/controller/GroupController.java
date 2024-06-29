package com.example.yoloq.controller;

import com.example.yoloq.elastic_models.GroupDocument;
import com.example.yoloq.elastic_services.GroupSearchService;
import com.example.yoloq.models.dto.GroupAdminDTO;
import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.models.dto.GroupRequestDTO;
import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.models.dto.requests.GroupJoinDecisionDTO;
import com.example.yoloq.models.dto.requests.CreateAdminDTO;
import com.example.yoloq.models.dto.requests.SuspendGroupDTO;
import com.example.yoloq.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/groups")
public class GroupController {
    private final GroupService groupService;
    private final GroupSearchService groupSearchService;

    @Autowired
    public GroupController(GroupService groupService, GroupSearchService groupSearchService) {
        this.groupService = groupService;
        this.groupSearchService = groupSearchService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<GroupDTO> save(@ModelAttribute GroupDTO groupDTO, @RequestParam(required = false) MultipartFile attachedPDF) {
        System.out.println(attachedPDF.getOriginalFilename());
        return new ResponseEntity<>(this.groupService.save(groupDTO, attachedPDF), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> findOne(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.findById(id), HttpStatus.OK);
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

    @PostMapping("/suspend")
    public ResponseEntity<GroupDTO> suspendGroup(@RequestBody SuspendGroupDTO suspendGroupDTO) {
        return new ResponseEntity<>(this.groupService.suspendGroup(suspendGroupDTO), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Set<GroupDTO>> getAllUserGroups(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.findAllGroupsForUser(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<GroupDocument>> findByName(@PathVariable String name) {
        return new ResponseEntity<>(this.groupSearchService.searchGroupsByName(name), HttpStatus.OK);
    }
}
