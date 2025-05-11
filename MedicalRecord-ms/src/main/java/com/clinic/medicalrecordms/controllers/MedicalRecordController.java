package com.clinic.medicalrecordms.controllers;

import com.clinic.medicalrecordms.dto.MedicalRecordRequestDTO;
import com.clinic.medicalrecordms.dto.MedicalRecordResponseDTO;
import com.clinic.medicalrecordms.entities.MedicalRecord;
import com.clinic.medicalrecordms.repository.MedicalRecordRepository;
import com.clinic.medicalrecordms.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping
    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @GetMapping("/{id}")
    public MedicalRecordResponseDTO getMedicalRecordById(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @PostMapping
    public MedicalRecordResponseDTO createMedicalRecord(@RequestBody MedicalRecordRequestDTO medicalRecordRequestDTO) {
        return medicalRecordService.createMedicalRecord(medicalRecordRequestDTO);
    }
    @PutMapping("/{id}")
    public MedicalRecordResponseDTO updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordRequestDTO medicalRecordRequestDTO) {
        return medicalRecordService.updateMedicalRecord(id, medicalRecordRequestDTO);

    }
    @DeleteMapping("/{id}")
    public void deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

}

