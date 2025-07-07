package com.javatechie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long doctorId;
    private Long patientId;
    private List<Integer> roleIds; // id des rôles sélectionnés
}
