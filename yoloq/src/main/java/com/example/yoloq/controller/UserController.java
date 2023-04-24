package com.example.yoloq.controller;
import com.example.yoloq.exception.ResourceNotFoundException;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.models.dto.requests.LoginRequestDTO;
import com.example.yoloq.models.dto.requests.RegisterRequestDTO;
import com.example.yoloq.models.dto.response.TokenResponse;
import com.example.yoloq.service.UserService;
import com.example.yoloq.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ResponseEntity<UserDTO> create(RegisterRequestDTO newUser) {
        return new ResponseEntity<>(this.service.save(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<TokenResponse> login(@RequestBody @Validated LoginRequestDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
            UserDTO userDTO = service.login(loginDTO.getUsername());
            return new ResponseEntity<>(new TokenResponse(tokenUtils.generateToken(userDetails, userDTO), (long) tokenUtils.getExpiredIn()), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            throw new ResourceNotFoundException("Username that you've entered is not found");
        }
    }
}
