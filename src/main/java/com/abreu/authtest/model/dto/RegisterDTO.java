package com.abreu.authtest.model.dto;

import com.abreu.authtest.model.enums.UserRole;

public record RegisterDTO(
        String name,
        String email,
        String username,
        String password,
        UserRole userRole
) {
}
