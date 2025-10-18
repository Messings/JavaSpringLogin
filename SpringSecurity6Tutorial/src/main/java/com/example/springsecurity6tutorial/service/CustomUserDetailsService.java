package com.example.springsecurity6tutorial.service;

import com.example.springsecurity6tutorial.CustomUserDetails;
import com.example.springsecurity6tutorial.models.User;
import com.example.springsecurity6tutorial.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component //tells spring this is a injectable object
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepository.findByUsername(username);
        if(Objects.isNull(user)){
            System.out.println("User not Available");
            throw new UsernameNotFoundException("user not found");
        }

        else{

            // get role type for the user: Admin or User
            List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleType()))
                    .toList();


            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword()) // ✅ must be the hashed password from DB
                    .authorities(authorities)
                    .build();
        }
    }
}
