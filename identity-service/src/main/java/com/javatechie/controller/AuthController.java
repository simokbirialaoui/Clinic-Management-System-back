package com.javatechie.controller;

import com.javatechie.dto.AuthRequest;
import com.javatechie.dto.UserResponseDto;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import com.javatechie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getEmail());
        } else {
            throw new RuntimeException("invalid access");
        }
    }


    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    // ... autres endpoints

    @GetMapping("/user")
    public UserResponseDto getCurrentUser(Principal principal) {
        String email = principal.getName();
        UserCredential user = userCredentialRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Extraire les noms des rôles
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());

        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), roleNames);
    }



}
