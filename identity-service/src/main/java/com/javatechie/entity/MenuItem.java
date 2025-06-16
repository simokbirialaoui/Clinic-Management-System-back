package com.javatechie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Clé i18n ex: "MENU.DASHBOARD"
    private String icon;  // ex: "tachometer-alt"
    private String path;  // ex: "/dashboard"

    // Optionnel : pour garder l'accès inverse (lecture seule)
    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles;
}