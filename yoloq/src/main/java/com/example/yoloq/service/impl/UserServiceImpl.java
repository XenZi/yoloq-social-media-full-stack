package com.example.yoloq.service.impl;

import com.example.yoloq.enums.Role;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.repository.UserRepository;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }
    @Override
    public UserDTO save(RegisterRequestDTO newUser) {
        User user = modelMapper.map(newUser, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImagePath("");
        User registeredUser = userRepository.save(user);
        return modelMapper.map(registeredUser, UserDTO.class);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Searching user is not found");
        }
        return user.get();
    }

    @Override
    public UserDTO login(String username) {
        return modelMapper.map(this.findByUsername(username), UserDTO.class);
    }

}
