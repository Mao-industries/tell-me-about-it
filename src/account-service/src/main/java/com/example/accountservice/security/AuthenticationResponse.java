package com.example.accountservice.security;

/**
 *
 * @author mBougueddach
 */
public record AuthenticationResponse(
    String username,
    String tokenType,
    String token
) {

}
