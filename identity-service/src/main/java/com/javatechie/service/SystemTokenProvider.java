package com.javatechie.service;

import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemTokenProvider {

    private final JwtService jwtService;
    private final UserCredentialRepository repository;

    private String systemToken;

    @Autowired
    public SystemTokenProvider(JwtService jwtService, UserCredentialRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    public String getSystemToken() {
        if (systemToken == null) {
            this.systemToken = generateSystemToken();
        }
        return systemToken;
    }

    private String generateSystemToken() {
        UserCredential user = repository.findByEmail("system@clinic.com") // correspond à celui créé par l'initializer
                .orElseThrow(() -> new RuntimeException("System user not found"));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        UserDetails userDetails = User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

        String token = jwtService.generateToken(userDetails);
        System.out.println("✅ Token système généré avec succès.");
        return token;
    }
}
