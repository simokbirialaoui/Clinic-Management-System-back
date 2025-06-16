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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;  // ex: "ROLE_ADMIN", "ROLE_USER"
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_routes",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    private Set<MenuItem> routes;
}
