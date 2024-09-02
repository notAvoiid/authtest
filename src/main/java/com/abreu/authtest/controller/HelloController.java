package com.abreu.authtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
@Tag(name = "Hello", description = "Endpoint for testing authentication")
public class HelloController {

    @GetMapping
    @Operation(summary = "Say hello to user!",
        description = "Say hello to user!",
        tags = {"Hello"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
        })
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "Hello " + authentication.getName();
    }

}
