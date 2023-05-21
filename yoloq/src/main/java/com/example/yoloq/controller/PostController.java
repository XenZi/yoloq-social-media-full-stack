package com.example.yoloq.controller;


import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> save(PostDTO postDTO) {
        return new ResponseEntity<>(this.postService.save(postDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll() {
        return new ResponseEntity<>(this.postService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findOne(@PathVariable int id) {
        return new ResponseEntity<>(this.postService.findOne(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(this.postService.update(postDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDTO> delete(@PathVariable int id) {
        return new ResponseEntity<>(this.postService.delete(id), HttpStatus.OK);
    }
}