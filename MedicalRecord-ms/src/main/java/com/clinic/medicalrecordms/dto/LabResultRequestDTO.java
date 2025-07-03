package com.clinic.medicalrecordms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResultRequestDTO {
    private String testName;
    private String result;
    private LocalDate testDate;
}
