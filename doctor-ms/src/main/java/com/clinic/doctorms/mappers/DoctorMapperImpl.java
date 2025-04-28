package com.clinic.doctorms.mappers;
import com.clinic.doctorms.dtos.DoctorDto;
import com.clinic.doctorms.entities.Doctor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorMapperImpl {

    public DoctorDto fromDoctor(Doctor doctor) {
        if (doctor == null) return null;
        DoctorDto dto = new DoctorDto();
        BeanUtils.copyProperties(doctor, dto);
        return dto;
    }

    public Doctor fromDoctorDTO(DoctorDto dto) {
        if (dto == null) return null;
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(dto, doctor);
        return doctor;
    }

    public List<DoctorDto> fromDoctorList(List<Doctor> doctors) {
        return doctors.stream()
                .map(this::fromDoctor)
                .collect(Collectors.toList());
    }

    public List<Doctor> fromDoctorDTOList(List<DoctorDto> doctorDTOs) {
        return doctorDTOs.stream()
                .map(this::fromDoctorDTO)
                .collect(Collectors.toList());
    }
}
