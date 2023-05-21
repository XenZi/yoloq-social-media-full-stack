package com.example.yoloq.controller;

import com.example.yoloq.models.dto.GroupDTO;
import com.example.yoloq.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
