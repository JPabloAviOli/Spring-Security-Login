package com.pavila.SecureLogin.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegisteredUserRequest implements Serializable {

    @NotBlank(message = "Firstname is required")
    private String firstname;
    @NotBlank(message = "Lastname is required")
    private String lastname;
    @Email(message = "Email is not formatted")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 8, message = " Password should be 8 characters long minimum")
    @NotBlank(message = " Password is required")
    private String password;
    @Size(min = 8, message = " Password should be 8 characters long minimum")
    @NotBlank(message = " Confirmation password is required")
    private String confirmationPassword;

}
