package com.clinic.medicalrecordms.dto;

import java.time.LocalDate;

public class LabResultRequestDTO {
    private Long id;

    private String testName;
    private String result;
    private LocalDate testDate;
}
