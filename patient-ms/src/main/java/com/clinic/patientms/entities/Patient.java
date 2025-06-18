package com.clinic.patientms.entities;
import com.clinic.patientms.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
}
