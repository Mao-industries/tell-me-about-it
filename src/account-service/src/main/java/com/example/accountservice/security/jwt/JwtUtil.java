package com.example.accountservice.security.jwt;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author mBougueddach
 */
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {
    private String secret = "secrectscret";

    private Long expiration = 70000l;

    public String generateToken(UserDetails userDetails) {
        String authorities = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        // create a map of claims to be included in the JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("authorities", authorities);
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (SignatureException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
    }

}
