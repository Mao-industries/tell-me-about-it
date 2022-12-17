/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.accountservice.domain.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.accountservice.data.mappers.UserMapper;
import com.example.accountservice.data.repositories.UserRepository;
import com.example.accountservice.domain.models.dtos.UserDTO;
import com.example.accountservice.domain.models.entities.User;
import com.example.accountservice.security.MyUserDetails;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author mBougueddach
 */

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserDTO signUp(UserDTO userDTO) {
        User newUser = userMapper.toEntity(userDTO);
        newUser.setDateOfCreation(LocalDate.now());
        newUser.setPassword(passwordEncoder.encode(userDTO.password()));
        return userMapper.toDTO(userRepository.save(newUser));
    }

    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalArgumentException());
        return new MyUserDetails(userMapper.toDTO(user));
    }

    public UserDTO authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username).get();
        if(passwordEncoder.matches(user.getPassword(), password)) {
            return userMapper.toDTO(user);
        }
        return null;
    }

}
