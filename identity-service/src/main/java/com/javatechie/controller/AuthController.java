package com.javatechie.controller;

import com.javatechie.dto.*;
import com.javatechie.entity.MenuItem;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import com.javatechie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
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
    @GetMapping("/user")
    public UserResponseDto getCurrentUser(Principal principal) {
        String email = principal.getName();
        UserCredential user = userCredentialRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<RoleDto> roleDtos = user.getRoles().stream()
                .map(role -> {
                    // Tri par order, puis titre
                    Set<MenuItemDto> menuDtos = role.getMenus().stream()
                            .sorted(Comparator
                                    .comparing(MenuItem::getOrder, Comparator.nullsLast(Integer::compareTo))
                                    .thenComparing(menu -> menu.getTitle().toLowerCase())
                            )
                            .map(menu -> new MenuItemDto(
                                    menu.getId(),
                                    menu.getTitle(),
                                    menu.getIcon(),
                                    menu.getPath(),
                                    menu.getOrder()  // ajouter si MenuItemDto contient order
                            ))
                            .collect(Collectors.toCollection(LinkedHashSet::new));

                    return new RoleDto(role.getName(), menuDtos);
                })
                .collect(Collectors.toSet());

        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                roleDtos
        );
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        service.forgotPassword(request.getEmail());
        return ResponseEntity.ok("Email de réinitialisation envoyé");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String email = service.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Mot de passe réinitialisé avec succès : " + email);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserCredential> users = userCredentialRepository.findAll();

        List<UserResponseDto> userDtos = users.stream()
                .map(user -> {
                    Set<RoleDto> roleDtos = user.getRoles().stream()
                            .map(role -> {
                                Set<MenuItemDto> menuDtos = role.getMenus().stream()
                                        .sorted(Comparator
                                                .comparing(MenuItem::getOrder, Comparator.nullsLast(Integer::compareTo))
                                                .thenComparing(menu -> menu.getTitle().toLowerCase())
                                        )
                                        .map(menu -> new MenuItemDto(
                                                menu.getId(),
                                                menu.getTitle(),
                                                menu.getIcon(),
                                                menu.getPath(),
                                                menu.getOrder()
                                        ))
                                        .collect(Collectors.toCollection(LinkedHashSet::new));
                                return new RoleDto(role.getName(), menuDtos);
                            })
                            .collect(Collectors.toSet());

                    return new UserResponseDto(
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getPhone(),
                            roleDtos
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }


}
