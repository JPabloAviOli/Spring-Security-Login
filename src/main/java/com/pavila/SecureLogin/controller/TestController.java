package com.pavila.SecureLogin.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("Test")
@Tag(name = "Test-controller")
public class TestController {

    @Secured("USER")
    @GetMapping
    public String test(){
        return "Test successfully";
    }
}
