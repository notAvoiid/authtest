package com.abreu.authtest.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Login Request DTO", description = "DTO for authentication request")
public record AuthenticationDTO(
        @Schema(description = "User username", example = "fulanodetal")
        String username,
        @Schema(description = "User password", example = "999")
        String password
) {
}
