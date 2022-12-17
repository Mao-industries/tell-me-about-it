/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.accountservice.data.mappers;

import org.springframework.stereotype.Component;

import com.example.accountservice.domain.models.dtos.UserDTO;
import com.example.accountservice.domain.models.entities.User;

/**
 *
 * @author mBougueddach
 */

@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.id())
                .username(userDTO.username().trim().toLowerCase())
                .email(userDTO.email().trim().toLowerCase())
                .fullName(userDTO.fullName().trim().toLowerCase())
                .title(userDTO.title().trim().toLowerCase())
                .dateOfBirth(userDTO.dateOfBirth())
                .dateOfCreation(userDTO.dateOfCreation())
                .organization(userDTO.organization().trim().toLowerCase())
                .verified(userDTO.verified())
                .build();
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getTitle(),
                user.getOrganization(),
                user.getDateOfBirth(),
                user.getDateOfCreation(),
                user.getVerified());
    }
}
