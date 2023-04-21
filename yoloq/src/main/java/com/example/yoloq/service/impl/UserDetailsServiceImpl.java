package com.example.yoloq.service.impl;

import com.example.yoloq.models.User;
import com.example.yoloq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = "ROLE_" + user.getRole().toString();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername().trim(),
                user.getPassword().trim(),
                grantedAuthorities);
    }
}
