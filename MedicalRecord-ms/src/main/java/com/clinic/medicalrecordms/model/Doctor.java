package com.clinic.medicalrecordms.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Doctor {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
}
