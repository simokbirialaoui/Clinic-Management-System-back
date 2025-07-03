package com.javatechie.dto;

import lombok.Data;

@Data
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
