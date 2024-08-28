package com.abreu.authtest.controller;

import com.abreu.authtest.model.dto.AuthenticationDTO;
import com.abreu.authtest.model.dto.RegisterDTO;
import com.abreu.authtest.repository.UserRepository;
import com.abreu.authtest.security.TokenService;
import com.abreu.authtest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.abreu.authtest.utils.AuthConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Nested
    class Login {

        @Test
        @WithMockUser(roles = {"ADMIN", "USER"})
        @DisplayName("Should Register User Successfully")
        void shouldRegisterUserSuccessfully() throws Exception{

            when(userService.register(any(RegisterDTO.class))).thenReturn(RESPONSE);

            var userJson = objectMapper.writeValueAsString(RESPONSE);

            mockMvc.perform(post(URL + "/register")
                    .contentType(APPLICATION_JSON)
                    .content(userJson)
                    .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))

                    .andExpect(jsonPath("$.username").value(RESPONSE.username()))
                    .andExpect(jsonPath("$.token").value(RESPONSE.token()))
                    .andExpect(jsonPath("$.expiresAt").value(RESPONSE.expiresAt()));

        }

        @Test
        @WithMockUser(roles = {"ADMIN", "USER"})
        @DisplayName("Should Login User Successfully")
        void shouldLoginUserSuccessfully() throws Exception{

            when(userService.login(any(AuthenticationDTO.class))).thenReturn(RESPONSE);

            var userJson = objectMapper.writeValueAsString(RESPONSE);

            mockMvc.perform(post(URL + "/login")
                            .contentType(APPLICATION_JSON)
                            .content(userJson)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))

                    .andExpect(jsonPath("$.username").value(RESPONSE.username()))
                    .andExpect(jsonPath("$.token").value(RESPONSE.token()))
                    .andExpect(jsonPath("$.expiresAt").value(RESPONSE.expiresAt()));

        }

    }

}
