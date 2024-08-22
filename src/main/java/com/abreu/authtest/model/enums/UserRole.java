package com.abreu.authtest.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private String userRole;

    UserRole(String role) {
        this.userRole = role;
    }
}
