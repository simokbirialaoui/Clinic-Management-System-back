package com.clinic.medicalrecordms.mappers;

import com.clinic.medicalrecordms.dto.LabResultResponseDTO;
import com.clinic.medicalrecordms.entities.LabResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class LabResultMapper {
    @Autowired
    private  MedicalRecordMapper medicalRecordMapper;

    public LabResultResponseDTO toResponseDTO(LabResult labResult) {

        LabResultResponseDTO labResultResponseDTO = new LabResultResponseDTO();
        BeanUtils.copyProperties(labResult, labResultResponseDTO);
        labResultResponseDTO.setMedicalRecordResponseDTO(medicalRecordMapper.toResponseDTO(labResult.getMedicalRecord()));
        return  labResultResponseDTO;
    }
}
