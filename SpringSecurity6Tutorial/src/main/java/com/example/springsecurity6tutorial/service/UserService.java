package com.example.springsecurity6tutorial.service;

import com.example.springsecurity6tutorial.models.User;
import com.example.springsecurity6tutorial.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public User register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // we are encrypting all passwords before sending to db
        return userRepository.save(user);
    }
}
