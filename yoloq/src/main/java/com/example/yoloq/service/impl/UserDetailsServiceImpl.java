package com.example.yoloq.service.impl;

import com.example.yoloq.models.User;
import com.example.yoloq.repository.UserRepository;
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
import java.util.Optional;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findFirstByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Email or password are incorrect!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = "ROLE_" + user.get().getRole().toString();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail().trim(),
                user.get().getPassword().trim(),
                grantedAuthorities);
    }
}
