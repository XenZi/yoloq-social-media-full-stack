package com.example.yoloq.service.impl;

import com.example.yoloq.enums.Role;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.Image;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.models.dto.requests.UpdatePasswordDTO;
import com.example.yoloq.repository.UserRepository;
import com.example.yoloq.service.FileService;
import com.example.yoloq.service.UserService;
import com.example.yoloq.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final TokenUtils tokenUtils;
    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            FileService fileService,
            TokenUtils tokenUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
        this.fileService = fileService;
        this.tokenUtils = tokenUtils;
    }


    @Override
    public UserDTO save(RegisterRequestDTO newUser) {
        User user = modelMapper.map(newUser, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String name = "";
        if (newUser.getProfileImage() != null) {
            name = fileService.uploadImage(newUser.getProfileImage());
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
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findFirstByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Searching user is not found");
        }
        return user.get();
    }

    @Override
    public User findLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return this.findByEmail(email);
    }

    @Override
    public UserDTO login(String email) {
        return modelMapper.map(this.findByEmail(email), UserDTO.class);
    }

    @Override
    public boolean isTheSamePassword(String enteredPassword, String userPassword) {
        return passwordEncoder.matches(enteredPassword, userPassword);
    }

    @Override
    public String updatePassword(UpdatePasswordDTO updatePasswordDTO, String token) {
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getRepeatedNewPassword())) {
            throw new BadCredentialsException("New password and repeated password aren't the same");
        }
        String username = tokenUtils.getUsernameFromToken(token);
        Optional<User> userDetails = userRepository.findFirstByUsername(username);
        if (userDetails.isEmpty()) {
            throw new RuntimeException("You are not logged in");
        }
        String password = userDetails.get().getPassword();
        if (!isTheSamePassword(updatePasswordDTO.getOldPassword(), password)) {
            throw new RuntimeException("Old password is wrong");
        }
        userDetails.get().setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        this.userRepository.save(userDetails.get());
        return "You have successfully updated your password";
    }
}
