package com.example.yoloq.service.impl;

import com.example.yoloq.enums.Role;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.Image;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.repository.ImageRepository;
import com.example.yoloq.repository.UserRepository;
import com.example.yoloq.service.FileService;
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
    private final FileService fileService;
    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
        this.fileService = fileService;
    }
    @Override
    public UserDTO save(RegisterRequestDTO newUser) {
        User user = modelMapper.map(newUser, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String name = "";
        if (newUser.getProfilePicture() != null) {
            name = fileService.uploadImage(newUser.getProfilePicture());
        }
        user.setProfileImage(name);
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
