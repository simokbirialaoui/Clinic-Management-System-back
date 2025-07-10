package com.javatechie.controller;

import com.javatechie.dto.*;
import com.javatechie.entity.MenuItem;
import com.javatechie.entity.Role;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.RoleRepository;
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
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> addNewUser(@RequestBody UserCredential user) {
        String result = service.saveUser(user);

        Map<String, Object> response = new HashMap<>();
        if ("Email déjà utilisé".equals(result)) {
            response.put("success", false);
            response.put("message", result);
            return ResponseEntity.status(409).body(response); // ⚠️ 409 Conflict pour doublon
        }

        response.put("success", true);
        response.put("message", result);
        return ResponseEntity.status(201).body(response); // 201 Created
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
                user.getPatientId(),   // existant
                user.getDoctorId(),    // nouveau champ à récupérer
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
        List<UserCredential> users = userCredentialRepository.findByDeletedFalse();

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
                            user.getPatientId(),   // déjà présent
                            user.getDoctorId(),    // nouveau champ ajouté ici
                            roleDtos
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }



    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO updatedUserDto) {
        Optional<UserCredential> optionalUser = userCredentialRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserCredential existingUser = optionalUser.get();
        existingUser.setFirstName(updatedUserDto.getFirstName());
        existingUser.setLastName(updatedUserDto.getLastName());
        existingUser.setEmail(updatedUserDto.getEmail());
        existingUser.setPhone(updatedUserDto.getPhone());

        // Mettre à jour les rôles si besoin
        if (updatedUserDto.getRoleIds() != null && !updatedUserDto.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findAllById(updatedUserDto.getRoleIds())
                    .stream().collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }

        userCredentialRepository.save(existingUser);
        return ResponseEntity.ok("Utilisateur mis à jour avec succès");
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        Optional<UserCredential> optionalUser = userCredentialRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserCredential user = optionalUser.get();

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

        UserResponseDto responseDto = new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getPatientId(),    // <-- déjà présent
                user.getDoctorId(),     // <-- ajout ici
                roleDtos
        );

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<UserCredential> optionalUser = userCredentialRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserCredential user = optionalUser.get();
        user.setDeleted(true);
        userCredentialRepository.save(user);

        return ResponseEntity.ok("Utilisateur marqué comme supprimé");
    }

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Principal principal) {
        String email = principal.getName();
        UserCredential user = userCredentialRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifie l'ancien mot de passe
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Ancien mot de passe incorrect");
        }

        // Met à jour avec le nouveau mot de passe encodé
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userCredentialRepository.save(user);

        return ResponseEntity.ok("Mot de passe mis à jour avec succès");
    }

}
