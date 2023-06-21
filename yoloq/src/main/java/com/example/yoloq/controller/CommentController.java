package com.example.yoloq.controller;


import com.example.yoloq.models.dto.CommentDTO;
import com.example.yoloq.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> save(@RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(this.commentService.save(commentDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getOne(@PathVariable Integer id) {
        return new ResponseEntity<>(this.commentService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Set<CommentDTO>> listAllForPost(@PathVariable Integer id) {
        return new ResponseEntity<>(this.commentService.listAllForPost(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Integer id, @RequestBody CommentDTO updatedComment) {
        return new ResponseEntity<>(this.commentService.update(updatedComment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDTO> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(this.commentService.delete(id), HttpStatus.OK);
    }
}
