package com.javatechie.controller;

import com.javatechie.entity.Role;
import com.javatechie.entity.MenuItem;
import com.javatechie.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    // ✅ Get all roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    // ✅ Create a new role
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleRepository.save(role));
    }

    // ✅ Get menu items (routes) by role name
    @GetMapping("/{roleName}/routes")
    public ResponseEntity<Set<MenuItem>> getRoutesByRole(@PathVariable String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        return role.map(value -> ResponseEntity.ok(value.getRoutes()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
