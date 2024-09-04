package com.pavila.SecureLogin.controller;

import com.pavila.SecureLogin.model.dto.RegisteredUserRequest;
import com.pavila.SecureLogin.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "User-controller")
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisteredUserRequest userRequest) throws MessagingException {
        authenticationService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
