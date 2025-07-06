package com.clinic.doctorms.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;

    @ElementCollection
    private List<DayOfWeek> availableDays;

    private LocalTime startTime; // e.g., 09:00
    private LocalTime endTime;   // e.g., 17:00
}
