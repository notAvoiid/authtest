package com.abreu.authtest.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Register an User DTO", description = "DTO for register an user")
public record RegisterDTO(
        @Schema(description = "User full name", example = "Fulano da Silva")
        String name,
        @Schema(description = "User email", example = "fulanodetal@gmail.com")
        String email,
        @Schema(description = "User username", example = "fulanodetal")
        String username,
        @Schema(description = "User password", example = "999")
        String password
) {
}
