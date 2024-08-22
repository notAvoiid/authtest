package com.abreu.authtest.service;

import com.abreu.authtest.model.User;
import com.abreu.authtest.model.dto.AuthenticationDTO;
import com.abreu.authtest.model.dto.LoginResponseDTO;
import com.abreu.authtest.model.dto.RegisterDTO;
import com.abreu.authtest.repository.UserRepository;
import com.abreu.authtest.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder encoder;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    @Transactional
    public LoginResponseDTO login(AuthenticationDTO data) {

        Optional<User> user = userRepository.findByUsername(data.username());
        if (user.isEmpty() || !encoder.matches(data.password(), user.get().getPassword())) throw new RuntimeException("test");

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        log.info("An user is authenticated!");

        return new LoginResponseDTO(auth.getName(), token, tokenService.getExpirationDateFromToken(token).toString());
    }

    @Transactional
    public LoginResponseDTO register(RegisterDTO data) {

        if (userRepository.existsByUsername(data.username()) || userRepository.existsByEmail(data.email())) throw new RuntimeException("test");
        User newUser = new User(data.name(), data.email(), data.username(), encoder.encode(data.password()), data.userRole());
        userRepository.save(newUser);

        var token = tokenService.generateToken(newUser);

        log.info("Registered an user!!");

        return new LoginResponseDTO(data.username(), token, tokenService.getExpirationDateFromToken(token).toString());
    }

}
