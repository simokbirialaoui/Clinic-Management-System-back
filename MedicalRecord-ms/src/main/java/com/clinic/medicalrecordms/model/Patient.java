package com.clinic.medicalrecordms.model;


import com.clinic.medicalrecordms.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter @Setter
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private Gender gender;
}
