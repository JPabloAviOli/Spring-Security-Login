package com.pavila.SecureLogin.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    @Size(min = 8, message = " Password should be 8 characters long minimum")
    @NotBlank(message = " Current Password is required")
    private String currentPassword;
    @Size(min = 8, message = " New Password should be 8 characters long minimum")
    @NotBlank(message = " New Password is required")
    private String newPassword;
    @Size(min = 8, message = " Password should be 8 characters long minimum")
    @NotBlank(message = " Confirmation password is required")
    private String confirmationPassword;
}
