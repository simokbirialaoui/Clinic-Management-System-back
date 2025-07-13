package com.clinic.doctorms.controllers;

import com.clinic.doctorms.dtos.DoctorDto;
import com.clinic.doctorms.entities.Doctor;
import com.clinic.doctorms.mappers.DoctorMapperImpl;
import com.clinic.doctorms.repositories.RepositoryDoctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private RepositoryDoctor repository;

    @Autowired
    private DoctorMapperImpl doctorMapper;

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = repository.findAll();
        return doctorMapper.fromDoctorList(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = repository.findById(id);
        return doctor.map(d -> ResponseEntity.ok(doctorMapper.fromDoctor(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DoctorDto createDoctor(@RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.fromDoctorDTO(doctorDto);
        Doctor savedDoctor = repository.save(doctor);
        return doctorMapper.fromDoctor(savedDoctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @RequestBody DoctorDto updatedDoctorDto) {
        return repository.findById(id).map(doctor -> {
            doctor.setFirstName(updatedDoctorDto.getFirstName());
            doctor.setLastName(updatedDoctorDto.getLastName());
            doctor.setEmail(updatedDoctorDto.getEmail());
            doctor.setPhone(updatedDoctorDto.getPhone());
            doctor.setSpecialization(updatedDoctorDto.getSpecialization());

            // ✅ Ajout des champs manquants
            doctor.setAvailableDays(updatedDoctorDto.getAvailableDays());
            doctor.setStartTime(updatedDoctorDto.getStartTime());
            doctor.setEndTime(updatedDoctorDto.getEndTime());

            Doctor updatedDoctor = repository.save(doctor);
            return ResponseEntity.ok(doctorMapper.fromDoctor(updatedDoctor));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
