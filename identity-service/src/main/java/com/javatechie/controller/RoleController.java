package com.javatechie.controller;

import com.javatechie.dto.MenuItemDto;
import com.javatechie.dto.RoleDto;
import com.javatechie.dto.RoleResponseDto;
import com.javatechie.entity.MenuItem;
import com.javatechie.entity.Role;
import com.javatechie.repository.MenuItemRepository;
import com.javatechie.repository.RoleRepository;
import com.javatechie.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable int id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }



    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        Set<MenuItem> menus = new HashSet<>(menuItemRepository.findAllById(dto.getMenuIds()));
        role.setMenus(menus);
        Role saved = roleRepository.save(role);
        return ResponseEntity.ok(mapToDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable int id, @RequestBody RoleDto roleDto) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setName(roleDto.getName());
                    Set<MenuItem> menus = new HashSet<>();
                    if (roleDto.getMenuIds() != null && !roleDto.getMenuIds().isEmpty()) {
                        menus = new HashSet<>(menuItemRepository.findAllById(roleDto.getMenuIds()));
                    }
                    role.setMenus(menus);
                    Role updated = roleRepository.save(role);
                    return ResponseEntity.ok(mapToDto(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        if (!roleRepository.existsById(id)) return ResponseEntity.notFound().build();
        roleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Mapper helper
    private RoleResponseDto mapToDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        Set<MenuItemDto> menuDtos = role.getMenus().stream()
                .map(m -> new MenuItemDto(m.getId(), m.getTitle(), m.getIcon(), m.getPath()))
                .collect(Collectors.toSet());
        dto.setMenus(menuDtos);
        return dto;
    }
}
