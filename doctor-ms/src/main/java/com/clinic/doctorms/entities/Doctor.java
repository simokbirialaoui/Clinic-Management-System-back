package com.clinic.doctorms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "doctor")
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
    @CollectionTable(
            name = "doctor_availabledays", // ✅ NOM EXACT DÉSIRÉ
            joinColumns = @JoinColumn(name = "doctor_id")
    )
    @Column(name = "available_day")
    private List<DayOfWeek> availableDays;

    private LocalTime startTime;
    private LocalTime endTime;
}
