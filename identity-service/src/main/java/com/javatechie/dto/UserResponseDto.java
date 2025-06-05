package com.javatechie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private int id;
    private String name;
    private String email;

    private Set<String> roles;  // Liste des noms de rôles (ex: ROLE_ADMIN, ROLE_USER)
}
