package com.pavila.SecureLogin.controller;

import com.pavila.SecureLogin.model.dto.ChangePasswordRequest;
import com.pavila.SecureLogin.model.dto.auth.AuthenticationRequest;
import com.pavila.SecureLogin.model.dto.auth.AuthenticationResponse;
import com.pavila.SecureLogin.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag( name = "Authentication-controller")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        AuthenticationResponse authResponse = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(authResponse);

    }

    @GetMapping("/activate-account")
    public ResponseEntity<?> confirm(@RequestParam String token) throws MessagingException {
        authenticationService.activateAccount(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        authenticationService.resetPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) throws MessagingException {
        authenticationService.updatePassword(token, newPassword);
        return ResponseEntity.ok().build();
    }

    @Secured("USER")
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request, Principal connectedUser){
        authenticationService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest){
        authenticationService.logout(httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @Hidden
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String jwt){
        boolean isTokenValid = authenticationService.isJwtValid(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

}
