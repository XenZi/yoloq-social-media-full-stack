package com.example.yoloq.controller;


import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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
    public ResponseEntity<PostDTO> save(@ModelAttribute PostDTO postDTO, @RequestParam(required = false) MultipartFile[] images) {
        return new ResponseEntity<>(this.postService.save(postDTO, images), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll() {
        return new ResponseEntity<>(this.postService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findOne(@PathVariable int id) {
        return new ResponseEntity<>(this.postService.findOne(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> update(@ModelAttribute PostDTO postDTO, @RequestParam(required = false) MultipartFile[] images) {
        return new ResponseEntity<>(this.postService.update(postDTO, images), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDTO> delete(@PathVariable int id) {
        return new ResponseEntity<>(this.postService.delete(id), HttpStatus.OK);
    }
}
