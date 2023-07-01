package com.example.yoloq.controller;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.dto.PostDTO;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.LoginRequestDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.models.dto.requests.UpdatePasswordDTO;
import com.example.yoloq.models.dto.response.GenericResponse;
import com.example.yoloq.models.dto.response.TokenResponse;
import com.example.yoloq.service.UserService;
import com.example.yoloq.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;


    @Autowired
    public UserController(UserService service, AuthenticationManager authenticationManager, TokenUtils tokenUtils, UserDetailsService userDetailsService) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDTO> create(@ModelAttribute RegisterRequestDTO newUser, @RequestParam MultipartFile profileImage) {
        return new ResponseEntity<>(this.service.save(newUser, profileImage), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Validated LoginRequestDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
            UserDTO userDTO = service.login(loginDTO.getEmail());
            return new ResponseEntity<>(new TokenResponse(tokenUtils.generateToken(userDetails, userDTO), (long) tokenUtils.getExpiredIn()), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            throw new ResourceNotFoundException("Username that you've entered is not found");
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/change-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {

        return new ResponseEntity<>(new GenericResponse(HttpStatus.OK, LocalDateTime.now().toString(), this.service.updatePassword(updatePasswordDTO)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/whoami")
    public ResponseEntity<UserDTO> whoAmI() {
        return new ResponseEntity<>(this.service.findLoggedUserForDTOResponse(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateDetails(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(this.service.updateDetails(userDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer id) {
        return new ResponseEntity<>(this.service.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/friends/{id}")
    public ResponseEntity<Set<UserDTO>> getAllFriendsForUser(@PathVariable Integer id) {
        return new ResponseEntity<>(this.service.getAllFriendsForUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/friends/{id}")
    public ResponseEntity<UserDTO> removeFriend(@PathVariable Integer id) {
        return new ResponseEntity<>(this.service.removeFriend(id), HttpStatus.OK);
    }
}
