package com.clinic.medicalrecordms.mappers;

import com.clinic.medicalrecordms.dto.MedicalRecordResponseDTO;
import com.clinic.medicalrecordms.entities.MedicalRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {
    public MedicalRecordResponseDTO toResponseDTO(MedicalRecord medicalRecord) {

        MedicalRecordResponseDTO medicalRecordResponseDTO = new MedicalRecordResponseDTO();

        BeanUtils.copyProperties(medicalRecord, medicalRecordResponseDTO);
        return  medicalRecordResponseDTO;
    }
    public MedicalRecord fromDTOToMedicalRecord(MedicalRecordResponseDTO medicalRecordResponseDTO){
        MedicalRecord medicalRecord=new MedicalRecord();
        BeanUtils.copyProperties(medicalRecordResponseDTO,medicalRecord);
        return medicalRecord;
    }
}
