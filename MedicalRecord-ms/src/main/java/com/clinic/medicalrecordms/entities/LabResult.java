package com.clinic.medicalrecordms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "labresult")
public class LabResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;
    private String result;
    private LocalDate testDate;

//    @ManyToOne
//    private MedicalRecord medicalRecord;

}
