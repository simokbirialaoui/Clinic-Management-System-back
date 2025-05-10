package com.clinic.appointmentms.dto;

import com.clinic.appointmentms.enums.Status;
import com.clinic.appointmentms.model.Doctor;
import com.clinic.appointmentms.model.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime appointmentDateTime;
    private Status status; // BOOKED, CANCELLED, COMPLETED

    private Long patientId;
    private Long doctorId;
    private Doctor doctor;
    private Patient patient;

}
