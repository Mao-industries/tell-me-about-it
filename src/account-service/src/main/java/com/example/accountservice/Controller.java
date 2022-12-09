/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.accountservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mBougueddach
 */

@RestController
public class Controller {

    @Autowired
    private final UserRepository userRepository;

    

    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public List<User> test() {
        List<User> user = userRepository.findAll();
        return user;
    }

    @PostMapping("/new")
    public User add() {
        return userRepository.save(new User(1L,"oussama", "oussama"));
    }
    
}
