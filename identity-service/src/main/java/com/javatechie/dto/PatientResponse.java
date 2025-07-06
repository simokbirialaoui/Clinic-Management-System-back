package com.javatechie.dto;

import lombok.Data;

@Data
public  class PatientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
