package com.pavila.SecureLogin.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RegisteredUserResponse {

    private Long id;
    private String fullName;
    private LocalDateTime dateOfBirth;
    private String email;
    private String jwt;

}
