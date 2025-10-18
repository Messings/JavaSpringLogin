package com.example.springsecurity6tutorial.controller;

import com.example.springsecurity6tutorial.models.User;
import com.example.springsecurity6tutorial.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.springsecurity6tutorial.repository.UserRepository;

import java.util.Objects;

@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @PostMapping("/register")
    public User register(@RequestBody User user){
        //return userRepository.save(user);
        return userService.register(user);
    }

/*
    @PostMapping("/login")
    public String login(@RequestBody User user){
        var u = userRepository.findByUsername(user.getUsername()); // currently bad, all needed for login is correct username
        if(!Objects.isNull(u)){
            return "success";
        } else
            return "failure";
    }*/


}
