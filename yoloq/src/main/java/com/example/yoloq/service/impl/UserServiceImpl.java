package com.example.yoloq.service.impl;

import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.mappers.UserDTOMapper;
import com.example.yoloq.mappers.UserMapperFromRegisterDTO;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.UserLoginDTO;
import com.example.yoloq.models.dto.UserRegisterDTO;
import com.example.yoloq.repository.UserRepository;
import com.example.yoloq.service.UserService;
import com.example.yoloq.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper mapper;
    private final UserMapperFromRegisterDTO userMapperFromRegisterDTO;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDTOMapper mapper, UserMapperFromRegisterDTO userMapperFromRegisterDTO, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userMapperFromRegisterDTO = userMapperFromRegisterDTO;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDTO save(UserRegisterDTO newUser) {
        User user = userMapperFromRegisterDTO.apply(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);

        return mapper.apply(registeredUser);
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
        return mapper.apply(this.findByUsername(username));
    }

}
