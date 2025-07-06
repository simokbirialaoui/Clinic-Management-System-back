package com.javatechie.service;

import com.javatechie.dto.MenuItemDto;
import com.javatechie.dto.RoleResponseDto;
import com.javatechie.entity.MenuItem;
import com.javatechie.entity.Role;
import com.javatechie.repository.MenuItemRepository;
import com.javatechie.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;
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

        // Tri des menus par ordre (champ order), puis par titre alphabétique si égal
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
                        menu.getOrder()  // N’oublie pas d’inclure order dans MenuItemDto !
                ))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        dto.setMenus(menuDtos);
        return dto;
    }


}