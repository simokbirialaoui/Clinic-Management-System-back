package com.clinic.medicalrecordms.service;

import com.clinic.medicalrecordms.dto.MedicalRecordRequestDTO;
import com.clinic.medicalrecordms.dto.MedicalRecordResponseDTO;
import com.clinic.medicalrecordms.entities.MedicalRecord;
import com.clinic.medicalrecordms.mappers.MedicalRecordMapper;
import com.clinic.medicalrecordms.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
     private MedicalRecordMapper medicalRecordMapper;

    @Override
    public MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO medicalRecordRequestDTO) {
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .date(medicalRecordRequestDTO.getDate())
                .diagnosis(medicalRecordRequestDTO.getDiagnosis())
                .prescription(medicalRecordRequestDTO.getPrescription())
                .notes(medicalRecordRequestDTO.getNotes())
                .build();

        MedicalRecord savedMedicalRecord1 = medicalRecordRepository.save(medicalRecord);

        MedicalRecordResponseDTO medicalRecordResponseDTO  = medicalRecordMapper.toResponseDTO(savedMedicalRecord1);

        return medicalRecordResponseDTO;
    }

    @Override
    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        List<MedicalRecordResponseDTO> medicalRecordResponseDTOS = medicalRecords.stream()
                .map(medicalRecordMapper::toResponseDTO)
                .collect(Collectors.toList());
        return medicalRecordResponseDTOS;
    }
    @Override
    public MedicalRecordResponseDTO getMedicalRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + id));
        return medicalRecordMapper.toResponseDTO(medicalRecord);
    }

    @Override
    public MedicalRecordResponseDTO updateMedicalRecord(Long id, MedicalRecordRequestDTO medicalRecordRequestDTO) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + id));

        medicalRecord.setDiagnosis(medicalRecordRequestDTO.getDiagnosis());
        medicalRecord.setDate(medicalRecordRequestDTO.getDate());
        medicalRecord.setNotes(medicalRecordRequestDTO.getNotes());
        medicalRecord.setPrescription(medicalRecordRequestDTO.getPrescription());

        MedicalRecord updatedMedicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toResponseDTO(updatedMedicalRecord);
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new RuntimeException("Medical record not found with id: " + id);
        }
        medicalRecordRepository.deleteById(id);
    }

}
