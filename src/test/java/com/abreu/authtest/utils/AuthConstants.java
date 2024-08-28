package com.abreu.authtest.utils;

import com.abreu.authtest.model.User;
import com.abreu.authtest.model.dto.AuthenticationDTO;
import com.abreu.authtest.model.dto.LoginResponseDTO;
import com.abreu.authtest.model.dto.RegisterDTO;
import com.abreu.authtest.model.enums.UserRole;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AuthConstants {

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ENCODED_PASSWORD = "encoded_password";
    public static final UserRole USER_ROLE = UserRole.USER;
    public static final String TOKEN = "token";
    public static final Instant EXPIRATION_DATE = Instant.now().plus(1, ChronoUnit.HOURS);
    public static final String URL = "/auth";

    public static final RegisterDTO REQUEST = new RegisterDTO(NAME, EMAIL, USERNAME, PASSWORD);
    public static final User USER_LOGIN = new User(1L, NAME, EMAIL, USERNAME, PASSWORD, USER_ROLE);
    public static final AuthenticationDTO AUTHENTICATION = new AuthenticationDTO(USERNAME, PASSWORD);
    public static final LoginResponseDTO RESPONSE = new LoginResponseDTO(USERNAME, TOKEN, EXPIRATION_DATE.toString());

    public static final String APPLICATION_JSON_VALUE = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";
}
