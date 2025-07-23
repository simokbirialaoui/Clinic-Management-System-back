package com.clinic.medicalrecordms.entities;

import com.clinic.medicalrecordms.model.Doctor;
import com.clinic.medicalrecordms.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Table(name = "medicalrecord")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;
    private String prescription;
    private String notes;
    private LocalDate date;

//    @OneToMany(mappedBy = "medicalRecord", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<LabResult> labResults;

    private Long patientId;
    private Long doctorId;

    @Transient
    @JsonIgnore
    private Patient patient;
    @Transient
    @JsonIgnore
    private Doctor doctor;
}
