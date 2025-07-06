package com.javatechie.service;

import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SystemTokenProvider {

    private String systemToken;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserCredentialRepository repository;

    @PostConstruct
    public void init() {
        UserCredential user = repository.findByEmail("system@internal.local")
                .orElseThrow(() -> new RuntimeException("System user not found"));

        UserDetails userDetails = User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .build();

        systemToken = jwtService.generateToken(userDetails);
        System.out.println("Token système initialisé : " + systemToken);
    }

    public String getSystemToken() {
        return systemToken;
    }
}
