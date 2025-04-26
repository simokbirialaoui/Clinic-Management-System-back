package com.clinic.appointmentms.dto;

import com.clinic.appointmentms.enums.Status;
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

}
