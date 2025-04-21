package com.clinic.notificationms.dto;

import lombok.Data;
import java.util.Date;

@Data
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String gender;
}
