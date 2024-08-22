package com.abreu.authtest.controller;

import com.abreu.authtest.model.dto.AuthenticationDTO;
import com.abreu.authtest.model.dto.LoginResponseDTO;
import com.abreu.authtest.model.dto.RegisterDTO;
import com.abreu.authtest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(APPLICATION_JSON).body(userService.register(data));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data) {
        return ResponseEntity.ok().contentType(APPLICATION_JSON).body(userService.login(data));
    }

}
