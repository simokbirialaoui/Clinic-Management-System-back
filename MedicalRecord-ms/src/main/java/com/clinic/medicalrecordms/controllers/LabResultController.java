package com.clinic.medicalrecordms.controllers;

import com.clinic.medicalrecordms.dto.LabResultResponseDTO;
import com.clinic.medicalrecordms.dto.MedicalRecordResponseDTO;
import com.clinic.medicalrecordms.entities.LabResult;
import com.clinic.medicalrecordms.mappers.LabResultMapper;
import com.clinic.medicalrecordms.repository.LabResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/lab-results")
public class LabResultController {

    @Autowired
    LabResultRepository labResultRepository;
    @Autowired
    LabResultMapper labResultMapper;

    @GetMapping
    public List<LabResultResponseDTO> getAllLabResults() {
        List<LabResult> labResults = labResultRepository.findAll();
        List<LabResultResponseDTO> labResultResponseDTOS = labResults.stream()
                .map(labResultMapper::toResponseDTO)
                .collect(Collectors.toList());
        return labResultResponseDTOS;
    }

}
