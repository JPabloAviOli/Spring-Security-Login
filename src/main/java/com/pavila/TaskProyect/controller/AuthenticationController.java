package com.pavila.TaskProyect.controller;

import com.pavila.TaskProyect.model.dto.UserProfileResponse;
import com.pavila.TaskProyect.model.dto.auth.AuthenticationRequest;
import com.pavila.TaskProyect.model.dto.auth.AuthenticationResponse;
import com.pavila.TaskProyect.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {


    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){

        AuthenticationResponse authResponse = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(authResponse);

    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> findMyProfile(){
       UserProfileResponse userProfile = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(userProfile);
    }
}
