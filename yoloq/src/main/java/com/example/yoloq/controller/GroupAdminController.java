package com.example.yoloq.controller;

import com.example.yoloq.models.dto.GroupAdminDTO;
import com.example.yoloq.service.GroupAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/group-admin")
public class GroupAdminController {

    private final GroupAdminService groupAdminService;

    @Autowired
    public GroupAdminController(GroupAdminService groupAdminService) {
        this.groupAdminService = groupAdminService;
    }

    @GetMapping("/{groupID}")
    public ResponseEntity<Set<GroupAdminDTO>> getAllAdminsForGroup(@PathVariable Integer groupID) {
        return new ResponseEntity<>(this.groupAdminService.findAllByGroupID(groupID), HttpStatus.OK);
    }


}
