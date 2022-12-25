// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */

package com.example.accountservice.presentation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.accountservice.security.AuthenticationRequest;
import com.example.accountservice.security.AuthenticationResponse;
import com.example.accountservice.security.MyUserDetailsService;
import com.example.accountservice.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;



// /**
//  *
//  * @author mBougueddach
//  */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.username(), authenticationRequest.password());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.username());

        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(userDetails.getUsername(), "Bearer", token));
    }

//     @RequestMapping(value = "/refresh", method = RequestMethod.GET)
//     public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
//         String token = request.getHeader("Authorization");
//         String username = jwtTokenUtil.getUsernameFromToken(token);
//         JWTUser user = (JWTUser) userDetailsService.loadUserByUsername(username);

//         if (jwtTokenUtil.canTokenBeRefreshed(token)) {
//             String refreshedToken = jwtTokenUtil.refreshToken(token);
//             return ResponseEntity.ok(new JWTResponse(refreshedToken));
//         } else {
//             return ResponseEntity.badRequest().body(null);
//         }
//     }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}