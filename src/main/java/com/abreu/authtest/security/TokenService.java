package com.abreu.authtest.security;

import com.abreu.authtest.model.User;
import com.abreu.authtest.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    private final UserRepository userRepository;

    @Value("${api.security.token.secret}")
    private String secretKey;

    private Algorithm algorithm;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public String generateToken(User data) {
        try {
            return JWT.create()
                    .withClaim("role", data.getUserRole().toString())
                    .withIssuer("authetest")
                    .withSubject(data.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(Date.from(getExpirationDate()))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new JWTCreationException("Error while generating token. Try again later!", ex);
        }
    }

    public boolean validateToken(String token) {
        try {
            algorithm = Algorithm.HMAC256(secretKey.getBytes());
            JWT.require(algorithm)
                    .withIssuer("authetest")
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(algorithm)
                .withIssuer("authetest")
                .build()
                .verify(token)
                .getSubject();
    }

    public String getRoleFromToken(String token) {
        return JWT.require(algorithm)
                .withIssuer("authetest")
                .build()
                .verify(token)
                .getClaim("role").asString();
    }

    public Instant getExpirationDateFromToken(String token) {
        return JWT.require(algorithm)
            .withIssuer("authetest")
            .build()
            .verify(token)
            .getExpiresAt()
            .toInstant();
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
