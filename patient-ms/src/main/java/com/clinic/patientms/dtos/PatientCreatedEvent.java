package com.clinic.patientms.dtos;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreatedEvent {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
