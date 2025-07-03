package com.javatechie.controller;
import com.javatechie.dto.MenuItemDto;
import com.javatechie.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menus")
    public ResponseEntity<List<MenuItemDto>> getAllMenus() {
        List<MenuItemDto> menus = menuService.getAllMenus(); // Méthode à créer
        return ResponseEntity.ok(menus);
    }

}
