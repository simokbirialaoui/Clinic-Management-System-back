package com.javatechie.service;

import com.javatechie.dto.MenuItemDto;
import com.javatechie.dto.RoleResponseDto;
import com.javatechie.entity.Role;
import com.javatechie.repository.MenuItemRepository;
import com.javatechie.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final MenuItemRepository menuItemRepository;

    public RoleResponseDto getRoleById(int id) {
        return roleRepository.findByIdWithMenus(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    public List<RoleResponseDto> getAllRoles() {
        return roleRepository.findAllWithMenus().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RoleResponseDto convertToDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(role.getId());
        dto.setName(role.getName());

        // Conversion des MenuItem en MenuItemDto
        Set<MenuItemDto> menuDtos = role.getMenus().stream()
                .map(menu -> new MenuItemDto(
                        menu.getId(),
                        menu.getTitle(),
                        menu.getIcon(),
                        menu.getPath()))
                .collect(Collectors.toSet());

        dto.setMenus(menuDtos);
        return dto;
    }
}