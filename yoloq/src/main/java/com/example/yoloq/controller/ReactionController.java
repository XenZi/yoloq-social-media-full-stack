package com.example.yoloq.controller;

import com.example.yoloq.models.dto.ReactionDTO;
import com.example.yoloq.service.ReactionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    @Autowired
    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping
    public ResponseEntity<ReactionDTO> create(@RequestBody ReactionDTO reactionDTO) {
        return new ResponseEntity<>(this.reactionService.create(reactionDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReactionDTO> delete(@PathVariable int id) {
        return new ResponseEntity<>(this.reactionService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Set<ReactionDTO>> getAllReactionsForComment(@PathVariable int id) {
        return new ResponseEntity<>(this.reactionService.findAllForComment(id), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Set<ReactionDTO>> getAllReactionsForPost(@PathVariable int id) {
        return new ResponseEntity<>(this.reactionService.findAllForPost(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReactionDTO> update(@RequestBody ReactionDTO reactionDTO) {
        return new ResponseEntity<>(this.reactionService.update(reactionDTO), HttpStatus.OK);
    }
}
