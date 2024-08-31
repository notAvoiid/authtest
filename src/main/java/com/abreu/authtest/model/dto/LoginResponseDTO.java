package com.abreu.authtest.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Login Response DTO", description = "DTO for authentication response")
public record LoginResponseDTO(
        String username,
        String token,
        String expiresAt
) {
}
