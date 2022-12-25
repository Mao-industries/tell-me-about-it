package com.example.accountservice.security;


public record AuthenticationResponse(
        String username,
        String tokenType,
        String token
) {

}
