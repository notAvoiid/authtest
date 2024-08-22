package com.abreu.authtest.model.dto;

public record LoginResponseDTO(
        String username,
        String token,
        String expiresAt
) {
}
