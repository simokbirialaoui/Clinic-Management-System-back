package com.javatechie.repository;

import com.javatechie.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRoles_Name(String roleName); // Exemple : "ROLE_ADMIN"
}
