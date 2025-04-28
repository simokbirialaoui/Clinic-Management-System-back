package com.clinic.medicalrecordms.service;

import com.clinic.medicalrecordms.dto.MedicalRecordRequestDTO;
import com.clinic.medicalrecordms.dto.MedicalRecordResponseDTO;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO medicalRecordRequestDTO);

    List<MedicalRecordResponseDTO> getAllMedicalRecords();

    MedicalRecordResponseDTO getMedicalRecordById(Long id);

    MedicalRecordResponseDTO updateMedicalRecord(Long id, MedicalRecordRequestDTO medicalRecordRequestDTO);

    void deleteMedicalRecord(Long id);
}
