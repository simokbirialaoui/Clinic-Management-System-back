package com.clinic.medicalrecordms.dto;

import com.clinic.medicalrecordms.entities.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResultResponseDTO {
    private Long id;

    private String testName;
    private String result;
    private LocalDate testDate;
//    private MedicalRecordResponseDTO medicalRecordResponseDTO;
}
