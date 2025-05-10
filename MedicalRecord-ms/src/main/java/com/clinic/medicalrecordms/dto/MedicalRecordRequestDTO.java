package com.clinic.medicalrecordms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordRequestDTO {
    private String diagnosis;
    private String prescription;
    private String notes;
    private LocalDate date;

    private Long patientId;
    private Long doctorId;

}
