package com.abreu.authtest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "Hello " + authentication.getName();
    }

}
