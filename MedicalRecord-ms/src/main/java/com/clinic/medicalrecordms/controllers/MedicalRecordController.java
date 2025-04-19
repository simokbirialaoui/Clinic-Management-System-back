package com.clinic.medicalrecordms.controllers;

import com.clinic.medicalrecordms.entities.MedicalRecord;
import com.clinic.medicalrecordms.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    @GetMapping("/{id}")
    public MedicalRecord getMedicalRecordById(@PathVariable Long id) {
        return medicalRecordRepository.findById(id).orElse(null);
    }

    @PostMapping
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
    @PutMapping("/{id}")
    public MedicalRecord updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord updatedMedicalRecord) {
        return medicalRecordRepository.findById(id)
                .map(medicalRecord -> {
                    medicalRecord.setDate(updatedMedicalRecord.getDate());
                    medicalRecord.setDiagnosis(updatedMedicalRecord.getDiagnosis());
                    medicalRecord.setPrescription(updatedMedicalRecord.getPrescription());
                    medicalRecord.setNotes(updatedMedicalRecord.getNotes());
                    return medicalRecordRepository.save(medicalRecord);
                })
                .orElseGet(() -> {
                    updatedMedicalRecord.setId(id);
                    return medicalRecordRepository.save(updatedMedicalRecord);
                });
    }
    @DeleteMapping("/{id}")
    public void deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordRepository.deleteById(id);
    }

}

