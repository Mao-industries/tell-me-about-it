/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.accountservice.domain.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * @author mBougueddach
 */
public class UserNotFoundException  extends GeneralException {

    public UserNotFoundException() {
        super(
                HttpStatus.FORBIDDEN.toString(),
                "email or username doesn't exist, user not found in the database"
        );
    }

}

