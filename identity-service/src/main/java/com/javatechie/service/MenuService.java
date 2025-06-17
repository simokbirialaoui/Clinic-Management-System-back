package com.javatechie.service;

import com.javatechie.dto.MenuItemDto;
import com.javatechie.entity.MenuItem;
import com.javatechie.entity.Role;
import com.javatechie.entity.UserCredential;
import com.javatechie.repository.MenuItemRepository;
import com.javatechie.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final UserCredentialRepository userRepo;
    private final MenuItemRepository menuRepo;

    public List<MenuItemDto> getMenusForUser(String email) {
        UserCredential user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return getMenuForUserRoles(roles); // Appelle une méthode existante
    }

    public List<MenuItemDto> getMenuForUserRoles(List<String> roles) {
        // TODO: ajouter logique de filtre selon les rôles, ici on récupère tout pour l'exemple
        List<MenuItem> menuItems = menuRepo.findAll();

        return menuItems.stream()
                .sorted(Comparator.comparing(MenuItem::getOrder, Comparator.nullsLast(Integer::compareTo))) // tri par order, null à la fin
                .map(menu -> new MenuItemDto(
                        menu.getId(),
                        menu.getTitle(),
                        menu.getIcon(),
                        menu.getPath(),
                        menu.getOrder()  // ajout du champ order
                ))
                .collect(Collectors.toList());
    }

    public List<MenuItemDto> getAllMenus() {
        List<MenuItem> menuItems = menuRepo.findAll();

        return menuItems.stream()
                .sorted(Comparator.comparing(MenuItem::getOrder, Comparator.nullsLast(Integer::compareTo))) // tri par order
                .map(menu -> new MenuItemDto(
                        menu.getId(),
                        menu.getTitle(),
                        menu.getIcon(),
                        menu.getPath(),
                        menu.getOrder()  // ajout du champ order
                ))
                .collect(Collectors.toList());
    }


}
