package com.clinic.patientms.mappers;
import com.clinic.patientms.dtos.PatientDTO;
import com.clinic.patientms.entities.Patient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientMapperImpl {

    public PatientDTO fromPatient(Patient patient) {
        if (patient == null) return null;
        PatientDTO dto = new PatientDTO();
        BeanUtils.copyProperties(patient, dto);
        return dto;
    }

    public Patient fromPatientDTO(PatientDTO dto) {
        if (dto == null) return null;
        Patient patient = new Patient();
        BeanUtils.copyProperties(dto, patient);
        return patient;
    }

    public List<PatientDTO> fromPatientList(List<Patient> patients) {
        return patients.stream()
                .map(this::fromPatient)
                .collect(Collectors.toList());
    }

    public List<Patient> fromPatientDTOList(List<PatientDTO> patientDTOs) {
        return patientDTOs.stream()
                .map(this::fromPatientDTO)
                .collect(Collectors.toList());
    }
}
