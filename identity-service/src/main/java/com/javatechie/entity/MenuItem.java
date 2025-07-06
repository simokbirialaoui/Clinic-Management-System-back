package com.javatechie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "roles")  // Évite les problèmes de boucle dans toString()
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 50)
    private String icon;

    @Column(nullable = false, length = 200)
    private String path;

    @Column(name = "display_order")  // nom de colonne personnalisé
    private Integer order;

    @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @Setter(AccessLevel.PRIVATE)  // Empêche l'accès direct au setter
    private Set<Role> roles = new HashSet<>();

    // Méthodes utilitaires pour gérer la relation bidirectionnelle
    public void addRole(Role role) {
        this.roles.add(role);
        role.getMenus().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getMenus().remove(this);
    }

    // Builder pattern
    public static MenuItemBuilder builder() {
        return new MenuItemBuilder();
    }

    public static class MenuItemBuilder {
        private Long id;
        private String title;
        private String icon;
        private String path;
        private Integer order;
        public MenuItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MenuItemBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MenuItemBuilder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public MenuItemBuilder path(String path) {
            this.path = path;
            return this;
        }

        public MenuItem build() {
            MenuItem menuItem = new MenuItem();
            menuItem.setId(id);
            menuItem.setTitle(title);
            menuItem.setIcon(icon);
            menuItem.setPath(path);
            menuItem.setOrder(order);
            return menuItem;
        }
    }
}