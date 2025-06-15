package com.javatechie.controller;

import com.javatechie.entity.Role;
import com.javatechie.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    // Récupérer tous les rôles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    // Récupérer un rôle par ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
        return roleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer un rôle par nom (ex: "ROLE_ADMIN")
    @GetMapping("/by-name/{roleName}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String roleName) {
        return roleRepository.findByName(roleName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les menus d’un rôle par nom simple (ex: "admin")
    @GetMapping("/{roleName}/menus")
    public ResponseEntity<?> getMenusByRoleName(@PathVariable String roleName) {
        String roleFullName = "ROLE_" + roleName.toUpperCase();
        return roleRepository.findByName(roleFullName)
                .map(role -> ResponseEntity.ok(role.getMenus()))
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un rôle
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role saved = roleRepository.save(role);
        return ResponseEntity.ok(saved);
    }

    // Mettre à jour un rôle
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable int id, @RequestBody Role updatedRole) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setName(updatedRole.getName());
                    role.setMenus(updatedRole.getMenus());
                    return ResponseEntity.ok(roleRepository.save(role));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un rôle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
