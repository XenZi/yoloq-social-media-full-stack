package com.example.yoloq.service.impl;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.exception.UnauthorizedAccessException;
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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
        logger.info("User with the email " + user.getEmail() + " have just registered");
        return userDTO;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        return user.orElseThrow(() -> new ResourceNotFoundException("Searching user is not found"));
    }

    @Override
    public UserDTO findUserById(int id) {
        User user = this.findById(id);
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        if (user.getProfileImage() != null) {
            dto.setProfileImage(user.getProfileImage().getName());
        } else {
            dto.setProfileImage("profile");
        }
        return dto;
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
        User user = this.findLoggedUser();
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        if (user.getProfileImage() != null) {
            dto.setProfileImage(user.getProfileImage().getName());
        } else {
            dto.setProfileImage("profile");
        }
        return dto;
    }

    @Override
    public User findById(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    public UserDTO updateDetails(UserDTO userDTO) {
        User loggedUser = this.findLoggedUser();
        if (loggedUser.getId() != userDTO.getId()) {
            throw new UnauthorizedAccessException("You are not authorized to make this change");
        }
        User user = this.findById(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user = userRepository.save(user);
        UserDTO userReturnDTO = modelMapper.map(user, UserDTO.class);
        userReturnDTO.setProfileImage("profile");
        if (user.getProfileImage() != null) {
            userReturnDTO.setProfileImage(user.getProfileImage().getName());
        }
        logger.info("User " + user.getEmail() + " have updated his details.");
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public Set<UserDTO> updateFriendships(Integer userId, Integer friendId) {
        User user = userRepository.findByIdWithFriends(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        User friend = userRepository.findByIdWithFriends(friendId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + friendId));
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
        Set<UserDTO> set = new HashSet<>();
        set.add(modelMapper.map(user, UserDTO.class));
        set.add(modelMapper.map(friend, UserDTO.class));
        return set;
    }

    @Override
    @Transactional
    public Set<UserDTO> getAllFriendsForUser(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found")).getFriends().stream().map(friend -> {
            UserDTO userReturnDTO = modelMapper.map(friend, UserDTO.class);
            userReturnDTO.setProfileImage("profile");
            if (friend.getProfileImage() != null) {
                userReturnDTO.setProfileImage(friend.getProfileImage().getName());
            }
            return userReturnDTO;
        }).collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public UserDTO removeFriend(int friendID) {
        User user = userRepository.findByIdWithFriends(this.findLoggedUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + this.findLoggedUser().getId()));
        User friend = userRepository.findByIdWithFriends(friendID)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + friendID));
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userRepository.save(user);
        userRepository.save(friend);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public Set<UserDTO> searchUsers(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName).stream().map((user) -> {
            UserDTO userReturnDTO = modelMapper.map(user, UserDTO.class);
            userReturnDTO.setProfileImage("profile");
            if (user.getProfileImage() != null) {
                userReturnDTO.setProfileImage(user.getProfileImage().getName());
            }
            return userReturnDTO;
        }).collect(Collectors.toSet());
    }

    @Override
    public UserDTO updateProfileImage(MultipartFile file) {
        User user = this.findLoggedUser();
        if (user.getProfileImage() != null) {
            user.getProfileImage().setProfileImageOf(null);
            imageService.update(user.getProfileImage());
        }
        Image image = fileService.uploadImage(file);
        image.setProfileImageOf(user);
        imageService.update(image);
        user.setProfileImage(image);
        userRepository.save(user);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProfileImage(user.getProfileImage().getName());
        return userDTO;
    }

    @Override
    public UserDTO login(String email) {
        logger.info("User with the email " + email + " have just logged in");

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
        logger.info("User " + user.getEmail() + " have changed password");
        return "You have successfully updated your password";
    }
}
