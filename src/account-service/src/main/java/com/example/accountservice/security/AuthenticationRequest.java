package com.example.accountservice.security;

public record AuthenticationRequest(
        String username,
        String password
) {

}
