package com.example.yoloq.service.impl;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.Image;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.models.dto.requests.UpdatePasswordDTO;
import com.example.yoloq.repository.UserRepository;
import com.example.yoloq.service.FileService;
import com.example.yoloq.service.ImageService;
import com.example.yoloq.service.PasswordService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final PasswordService passwordService;
    private final ImageService imageService;
    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            FileService fileService,
            ImageService imageService,
            PasswordService passwordService) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
        this.fileService = fileService;
        this.imageService = imageService;
        this.passwordService = passwordService;
    }


    @Override
    public UserDTO save(RegisterRequestDTO newUser, MultipartFile profileImage) {
        User user = modelMapper.map(newUser, User.class);
        user.setPassword(passwordService.encodePassword(newUser.getPassword()));
        Image image = null;
        if (profileImage != null) {
            image = fileService.uploadImage(profileImage);
        }
        user.setProfileImage(image);
        User registeredUser = userRepository.save(user);
        UserDTO userDTO = modelMapper.map(registeredUser, UserDTO.class);
        if (image != null) {
            image.setProfileImageOf(user);
            imageService.update(image);
            userDTO.setProfileImage(image.getName());
        }
        return userDTO;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        return user.orElseThrow(() -> new ResourceNotFoundException("Searching user is not found"));
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findFirstByEmail(email);
        return user.orElseThrow(() -> new ResourceNotFoundException("Searching user is not found"));
    }

    @Override
    public User findLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return this.findByEmail(email);
    }

    @Override
    public UserDTO findLoggedUserForDTOResponse() {
        return this.modelMapper.map(this.findLoggedUser(), UserDTO.class);
    }

    @Override
    public UserDTO login(String email) {
        return modelMapper.map(this.findByEmail(email), UserDTO.class);
    }

    private boolean isTheSamePassword(String enteredPassword, String userPassword) {
        return passwordService.checkPassword(enteredPassword, userPassword);
    }

    @Override
    public String updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getRepeatedNewPassword())) {
            throw new BadCredentialsException("New password and repeated password aren't the same");
        }
        User user = this.findLoggedUser();
        if (!isTheSamePassword(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is wrong");
        }
        user.setPassword(passwordService.encodePassword(updatePasswordDTO.getNewPassword()));
        this.userRepository.save(user);
        return "You have successfully updated your password";
    }
}
