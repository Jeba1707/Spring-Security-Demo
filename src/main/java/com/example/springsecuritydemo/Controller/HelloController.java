package com.example.springsecuritydemo.Controller;

import com.example.springsecuritydemo.Entity.User;
import com.example.springsecuritydemo.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        } catch (Exception e) {
            return "Invalid user name or pass";
        }
        return "Login Failed";
    }
}



