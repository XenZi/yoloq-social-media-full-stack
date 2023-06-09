package com.example.yoloq.service.impl;

import com.example.yoloq.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean checkPassword(String enteredPassword, String encodedPassword) {
        return passwordEncoder.matches(enteredPassword, encodedPassword);
    }
}

