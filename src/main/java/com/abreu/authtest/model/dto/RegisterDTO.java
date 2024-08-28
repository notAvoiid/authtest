package com.abreu.authtest.model.dto;

public record RegisterDTO(
        String name,
        String email,
        String username,
        String password
) {
}
