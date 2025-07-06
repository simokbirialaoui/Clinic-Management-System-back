// RoleResponseDto.java
package com.javatechie.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RoleResponseDto {
    private Integer id;
    private String name;
    private Set<MenuItemDto> menus;
}
