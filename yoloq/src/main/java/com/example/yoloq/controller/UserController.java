package com.example.yoloq.controller;


import com.example.yoloq.models.User;
import com.example.yoloq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
    }

}
