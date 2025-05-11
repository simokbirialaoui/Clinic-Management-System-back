package com.clinic.medicalrecordms.entities;

import com.clinic.medicalrecordms.model.Doctor;
import com.clinic.medicalrecordms.model.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;
    private String prescription;
    private String notes;
    private LocalDate date;

    @OneToMany(mappedBy = "medicalRecord", fetch = FetchType.EAGER)
    private List<LabResult> labResults;

    private Long patientId;
    private Long doctorId;

    @Transient
    private Patient patient;
    @Transient
    private Doctor doctor;
}
