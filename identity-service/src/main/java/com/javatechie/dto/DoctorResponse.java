package com.javatechie.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
@Data
public class DoctorResponse {
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
