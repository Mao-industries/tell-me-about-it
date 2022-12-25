package com.example.accountservice.domain.services;

import com.example.accountservice.data.mappers.UserAccountMapper;
import com.example.accountservice.data.repositories.UserAccountRepository;
import com.example.accountservice.domain.exceptions.EmailTakenException;
import com.example.accountservice.domain.exceptions.UserNotFoundException;
import com.example.accountservice.domain.exceptions.UsernameTakenException;
import com.example.accountservice.domain.models.dtos.UserAccountDTO;
import com.example.accountservice.domain.models.entities.UserAccount;
import com.example.accountservice.security.MyUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userRepository;
    private final UserAccountMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    public UserAccountDTO createUser(UserAccountDTO userDTO) {
        userRepository.findByUsername(userDTO.username())
                .ifPresent(user -> {
                    throw new UsernameTakenException();
                });
        userRepository.findByEmail(userDTO.email())
                .ifPresent((user) -> {
                    throw new EmailTakenException();
                });
        UserAccount newUser = userMapper.toEntity(userDTO);
        newUser.setDateOfCreation(LocalDate.now());
        newUser.setPassword(passwordEncoder.encode(userDTO.password()));

        return userMapper.toDTO(userRepository.save(newUser));
    }

    public UserAccountDTO getUserAccount(String usernameOrEmail) {
        UserAccount user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException());
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserAccountDTO updateFullName(String usernameOrEmail, String fullname) {
        UserAccount user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException());
        user.setFullName(fullname);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserAccountDTO updateEmail(String usernameOrEmail, String email) {
        UserAccount user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException());
        user.setEmail(email);
        return userMapper.toDTO(user);
    }

    @Transactional
    public void updatePassword(String usernameOrEmail, String password) {
        UserAccount user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException());
        user.setPassword(passwordEncoder.encode(password));
    }

    @Transactional
    public UserAccountDTO updateTitle(String usernameOrEmail, String title) {
        UserAccount user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException());
        user.setTitle(title);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserAccountDTO updateOrganization(String usernameOrEmail, String organization) {
        UserAccount user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException());
        user.setOrganization(organization);
        return userMapper.toDTO(user);
    }

    @Transactional
    public void uploadProfilePicture(MultipartFile picture, String username) throws IOException {
        UserAccount user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UserNotFoundException());
        user.setPicture(picture.getBytes());
    }

    public byte[] viewProfilePicture(String username) throws IOException {
        UserAccount user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UserNotFoundException());
        byte[] picture;
        if (user.getPicture() != null) {
            picture = user.getPicture();
        } else {
            picture = new byte[5];
        }
        return picture;
    }

    public List<UserAccountDTO> getAllUsers() {
        List<UserAccount> userAccounts = userRepository.findAll();
        return userMapper.toDTO(userAccounts);
    }

    public UserDetails loadUserByUsername(String username) {
        UserAccount user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UserNotFoundException());
        return new MyUserDetails(userMapper.toDTO(user));
    }
}
