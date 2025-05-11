package com.clinic.appointmentms.entities;

import com.clinic.appointmentms.enums.Status;
import com.clinic.appointmentms.model.Doctor;
import com.clinic.appointmentms.model.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime appointmentDateTime;
    @Enumerated(EnumType.STRING)
    private Status status; // BOOKED, CANCELLED, COMPLETED

    private Long patientId;
    private Long doctorId;

    @Transient
    private Patient patient;
    @Transient
    private Doctor doctor;

}
