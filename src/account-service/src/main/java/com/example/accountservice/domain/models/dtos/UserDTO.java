package com.example.accountservice.domain.models.dtos;

import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author mBougueddach
 */

public record UserDTO(
    UUID id,
    String username,
    String password,
    String email,
    String fullName,
    String title,
    String organization,
    LocalDate dateOfBirth,
    LocalDate dateOfCreation,
    Boolean verified
) {

}
