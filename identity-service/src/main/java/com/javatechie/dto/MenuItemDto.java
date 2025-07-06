// MenuItemDto.java
package com.javatechie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {
    private Long id;
    private String title;
    private String icon;
    private String path;
    private Integer order;
}
