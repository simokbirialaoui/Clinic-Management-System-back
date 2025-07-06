package com.clinic.medicalrecordms.service;

import com.clinic.medicalrecordms.dto.MedicalRecordRequestDTO;
import com.clinic.medicalrecordms.dto.MedicalRecordResponseDTO;
import com.clinic.medicalrecordms.entities.MedicalRecord;
import com.clinic.medicalrecordms.feign.DoctorRestClient;
import com.clinic.medicalrecordms.feign.PatientRestClient;
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
    @Autowired
    DoctorRestClient doctorRestClient;
    @Autowired
    PatientRestClient patientRestClient;

    @Override
    public MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO medicalRecordRequestDTO) {
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .date(medicalRecordRequestDTO.getDate())
                .diagnosis(medicalRecordRequestDTO.getDiagnosis())
                .prescription(medicalRecordRequestDTO.getPrescription())
                .notes(medicalRecordRequestDTO.getNotes())
                .patientId(medicalRecordRequestDTO.getPatientId())
                .doctorId(medicalRecordRequestDTO.getDoctorId())
                .doctor(doctorRestClient.getDoctorById(medicalRecordRequestDTO.getDoctorId()))
                .patient(patientRestClient.getPatientById(medicalRecordRequestDTO.getPatientId()))
                .build();


        MedicalRecord savedMedicalRecord1 = medicalRecordRepository.save(medicalRecord);

        MedicalRecordResponseDTO medicalRecordResponseDTO = medicalRecordMapper.toResponseDTO(savedMedicalRecord1);

        return medicalRecordResponseDTO;
    }

    @Override
    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        medicalRecords.forEach(medicalRecord -> {
            medicalRecord.setDoctor(doctorRestClient.getDoctorById(medicalRecord.getDoctorId()));
            medicalRecord.setPatient(patientRestClient.getPatientById(medicalRecord.getPatientId()));
        });

        List<MedicalRecordResponseDTO> medicalRecordResponseDTOS = medicalRecords.stream()
                .map(medicalRecordMapper::toResponseDTO)
                .collect(Collectors.toList());
        return medicalRecordResponseDTOS;
    }

    @Override
    public MedicalRecordResponseDTO getMedicalRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + id));

        medicalRecord.setDoctor(doctorRestClient.getDoctorById(medicalRecord.getDoctorId()));
        medicalRecord.setPatient(patientRestClient.getPatientById(medicalRecord.getPatientId()));
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
        updatedMedicalRecord.setDoctor(doctorRestClient.getDoctorById(updatedMedicalRecord.getDoctorId()));
        updatedMedicalRecord.setPatient(patientRestClient.getPatientById(updatedMedicalRecord.getPatientId()));
        return medicalRecordMapper.toResponseDTO(updatedMedicalRecord);
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        // Sécurité : nettoyer les champs @Transient
        record.setDoctor(null);
        record.setPatient(null);

        // Supprimer les labResults s'ils dépendent de record
       // record.getLabResults().clear();

        medicalRecordRepository.delete(record);
    }

}
