package com.javatechie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItemDto {
    private Long id;
    private String title;
    private String icon;
    private String path;
}
