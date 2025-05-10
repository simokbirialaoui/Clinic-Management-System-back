package com.clinic.medicalrecordms.dto;

import com.clinic.medicalrecordms.entities.LabResult;
import com.clinic.medicalrecordms.model.Doctor;
import com.clinic.medicalrecordms.model.Patient;
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
public class MedicalRecordResponseDTO {

    private Long id;

    private String diagnosis;
    private String prescription;
    private String notes;
    private LocalDate date;

    private Long patientId;
    private Long doctorId;
    private Doctor doctor;
    private Patient patient;

}
