package com.clinic.doctorms.dtos;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private List<DayOfWeek> availableDays;
    private LocalTime startTime;
    private LocalTime endTime;
}