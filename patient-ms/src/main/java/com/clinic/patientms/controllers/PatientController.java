package com.clinic.patientms.controllers;
import com.clinic.patientms.dtos.PatientDTO;
import com.clinic.patientms.entities.Patient;
import com.clinic.patientms.kafka.PatientProducer;
import com.clinic.patientms.mappers.PatientMapperImpl;
import com.clinic.patientms.repositories.RepositoryPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientProducer patientProducer;

    @Autowired
    private RepositoryPatient repositoryPatient;

    @Autowired
    private PatientMapperImpl patientMapper;

    // 🔹 GET: liste tous les patients
    @GetMapping
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = repositoryPatient.findAll();
        return patientMapper.fromPatientList(patients);
    }

    // 🔹 GET: récupérer un patient par son ID
    @GetMapping("/{id}")
    public PatientDTO getPatientById(@PathVariable Long id) {
        Patient patient = repositoryPatient.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return patientMapper.fromPatient(patient);
    }

    // 🔹 POST: ajouter un nouveau patient
    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientDTO patientDTO) {
        Patient patient = patientMapper.fromPatientDTO(patientDTO);
        Patient savedPatient = repositoryPatient.save(patient);
      //  patientProducer.sendPatientData(savedPatient);
        return patientMapper.fromPatient(savedPatient);
    }

    // 🔹 PUT: mettre à jour un patient
    @PutMapping("/{id}")
    public PatientDTO updatePatient(@PathVariable Long id, @RequestBody PatientDTO updatedDTO) {
        Patient existingPatient = repositoryPatient.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        existingPatient.setFirstName(updatedDTO.getFirstName());
        existingPatient.setLastName(updatedDTO.getLastName());
        existingPatient.setEmail(updatedDTO.getEmail());
        existingPatient.setPhone(updatedDTO.getPhone());
        existingPatient.setDateOfBirth(updatedDTO.getDateOfBirth());
        existingPatient.setGender(updatedDTO.getGender());

        Patient updatedPatient = repositoryPatient.save(existingPatient);
        return patientMapper.fromPatient(updatedPatient);
    }

    // 🔹 DELETE: supprimer un patient
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        repositoryPatient.deleteById(id);
    }
}
