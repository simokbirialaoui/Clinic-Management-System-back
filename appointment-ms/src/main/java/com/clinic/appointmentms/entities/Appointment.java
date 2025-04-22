package com.clinic.appointmentms.entities;

import com.clinic.appointmentms.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity @NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patientId;
    private LocalDateTime appointmentDateTime;
    @Enumerated(EnumType.STRING)
    private Status status; // BOOKED, CANCELLED, COMPLETED


}
