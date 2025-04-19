package com.clinic.doctorms.controllers;

import com.clinic.doctorms.entities.Doctor;
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

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = repository.findById(id);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return repository.save(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
        return repository.findById(id).map(doctor -> {
            doctor.setFirstName(updatedDoctor.getFirstName());
            doctor.setLastName(updatedDoctor.getLastName());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setPhone(updatedDoctor.getPhone());
            doctor.setSpecialization(updatedDoctor.getSpecialization());
            repository.save(doctor);
            return ResponseEntity.ok(doctor);
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
