package com.example.accountservice.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.accountservice.domain.models.dtos.UserAccountDTO;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {

    private final UserAccountDTO userDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority(userDTO.userRole().name()));
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return userDTO.password();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return userDTO.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}

