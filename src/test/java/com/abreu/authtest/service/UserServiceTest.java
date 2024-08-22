package com.abreu.authtest.service;

import com.abreu.authtest.model.User;
import com.abreu.authtest.repository.UserRepository;
import com.abreu.authtest.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.abreu.authtest.utils.AuthConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService authService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    @Nested
    class Register {

        @Test
        @DisplayName("Should register user successfully and return a valid login response")
        void shouldRegisterUserSuccessfully() {
            when(userRepository.existsByUsername(USERNAME)).thenReturn(false);
            when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
            when(encoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
            when(tokenService.generateToken(any(User.class))).thenReturn(TOKEN);
            when(tokenService.getExpirationDateFromToken(TOKEN)).thenReturn(EXPIRATION_DATE);

            var response = authService.register(REQUEST);

            assertNotNull(response);
            assertEquals(RESPONSE.getClass(), response.getClass());
            assertEquals(RESPONSE.username(), response.username());
            assertEquals(RESPONSE.token(), response.token());
            assertEquals(RESPONSE.expiresAt(), response.expiresAt());

            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("Should Throw RuntimeException When Username Exists")
        void shouldThrowRuntimeExceptionWhenUsernameExists() {
            when(userRepository.existsByUsername(USERNAME)).thenReturn(true);

            var response = assertThrows(RuntimeException.class, () -> authService.register(REQUEST));

            assertNotNull(response);
            assertEquals("test", response.getMessage());

            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        @DisplayName("Should Throw RuntimeException When Email Exists")
        void shouldThrowRuntimeExceptionWhenEmailExists() {
            when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

            var response = assertThrows(RuntimeException.class, () -> authService.register(REQUEST));

            assertNotNull(response);
            assertEquals("test", response.getMessage());

            verify(userRepository, never()).save(any(User.class));
        }
    }

    @Nested
    class Login {

        @Test
        @DisplayName("Should return LoginResponseDTO when credentials are valid")
        void shouldReturnLoginResponseWhenCredentialsAreValid() {

            when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(USER_LOGIN));
            when(encoder.matches(PASSWORD, USER_LOGIN.getPassword())).thenReturn(true);
            when(tokenService.getExpirationDateFromToken(TOKEN)).thenReturn(EXPIRATION_DATE);

            var auth = mock(Authentication.class);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
            when(auth.getPrincipal()).thenReturn(USER_LOGIN);
            when(auth.getName()).thenReturn(USERNAME);

            when(tokenService.generateToken(USER_LOGIN)).thenReturn(TOKEN);

            var response = authService.login(AUTHENTICATION);

            assertNotNull(response);
            assertEquals(RESPONSE.getClass(), response.getClass());
            assertEquals(RESPONSE.username(), response.username());
            assertEquals(RESPONSE.token(), response.token());
            assertEquals(RESPONSE.expiresAt(), response.expiresAt());

            verify(userRepository, times(1)).findByUsername(USERNAME);
            verify(encoder, times(1)).matches(PASSWORD, USER_LOGIN.getPassword());
            verify(tokenService, times(1)).generateToken(any(User.class));
            verify(tokenService, times(1)).getExpirationDateFromToken(TOKEN);
        }

        @Test
        @DisplayName("Should Throw RuntimeException When User Not Found")
        void shouldThrowRuntimeExceptionWhenUserNotFound() {
            when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

            var exception = assertThrows(RuntimeException.class, () -> authService.login(AUTHENTICATION));

            assertNotNull(exception);
            assertEquals("test", exception.getMessage());
        }

        @Test
        @DisplayName("Should Throw RuntimeException When Password Is Incorrect")
        void shouldThrowExceptionWhenPasswordIsIncorrect() {
            when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(USER_LOGIN));
            when(encoder.matches(PASSWORD, USER_LOGIN.getPassword())).thenReturn(false);

            var exception = assertThrows(RuntimeException.class, () -> authService.login(AUTHENTICATION));

            assertNotNull(exception);
            assertEquals("test", exception.getMessage());
        }


    }
}
