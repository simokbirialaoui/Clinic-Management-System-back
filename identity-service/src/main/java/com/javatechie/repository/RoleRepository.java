package com.javatechie.repository;

import com.javatechie.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @EntityGraph(attributePaths = "menus")
    @Query("SELECT r FROM Role r WHERE r.id = :id")
    Optional<Role> findByIdWithMenus(@Param("id") int id);

    @EntityGraph(attributePaths = "menus")
    @Query("SELECT r FROM Role r")
    List<Role> findAllWithMenus();
}
