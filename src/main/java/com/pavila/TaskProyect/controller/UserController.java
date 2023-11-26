package com.pavila.TaskProyect.controller;

import com.pavila.TaskProyect.model.dto.RegisteredUserRequest;
import com.pavila.TaskProyect.model.dto.RegisteredUserResponse;
import com.pavila.TaskProyect.model.dto.UserRegisteredResponse;
import com.pavila.TaskProyect.service.IUserService;
import com.pavila.TaskProyect.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IUserService userService;

    @PostMapping("registered")
    public ResponseEntity<RegisteredUserResponse> registerOne(@RequestBody @Valid RegisteredUserRequest userRequest){
        RegisteredUserResponse registeredUserResponse = authenticationService.registerOneUserAuth(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserResponse);
    }

    @GetMapping("all")
    public ResponseEntity<List<UserRegisteredResponse>> findAll(){
        List<UserRegisteredResponse> userProfileResponses = userService.allUser();
        return ResponseEntity.ok(userProfileResponses);
    }
}
