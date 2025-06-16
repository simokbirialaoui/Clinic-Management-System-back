// RoleDto.java
package com.javatechie.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RoleDto {
    private String name;
    private Set<Long> menuIds;
}
