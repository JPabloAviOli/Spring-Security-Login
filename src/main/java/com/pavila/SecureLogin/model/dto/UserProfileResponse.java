package com.pavila.SecureLogin.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserProfileResponse implements Serializable {

    private String fullName;
    private LocalDateTime dateOfBirth;
    private String email;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;



}
