package com.abreu.authtest.controller;

import com.abreu.authtest.model.dto.AuthenticationDTO;
import com.abreu.authtest.model.dto.LoginResponseDTO;
import com.abreu.authtest.model.dto.RegisterDTO;
import com.abreu.authtest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoint for managing authentication")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register an User!",
            description = "Register an User by passing in a JSON representation!",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(APPLICATION_JSON).body(userService.register(data));
    }

    @PostMapping("/login")
    @Operation(summary = "Log in an User!",
            description = "Log in an User by passing in a JSON representation!",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data) {
        return ResponseEntity.ok().contentType(APPLICATION_JSON).body(userService.login(data));
    }

}
