package com.clinic.patientms.controllers;
import com.clinic.patientms.entities.Patient;
import com.clinic.patientms.kafka.PatientProducer;
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

    // 🔹 GET: liste tous les patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return repositoryPatient.findAll();
    }

    // 🔹 GET: récupérer un patient par son ID
    @GetMapping("/{id}")
    public Optional<Patient> getPatientById(@PathVariable Long id) {
        return repositoryPatient.findById(id);
    }

    // 🔹 POST: ajouter un nouveau patient


    // 🔹 PUT: mettre à jour un patient
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        return repositoryPatient.findById(id)
                .map(patient -> {
                    patient.setFirstName(updatedPatient.getFirstName());
                    patient.setLastName(updatedPatient.getLastName());
                    patient.setEmail(updatedPatient.getEmail());
                    patient.setPhone(updatedPatient.getPhone());
                    patient.setGender(updatedPatient.getGender());
                    patient.setDateOfBirth(updatedPatient.getDateOfBirth());
                    return repositoryPatient.save(patient);
                })
                .orElseGet(() -> {
                    updatedPatient.setId(id);
                    return repositoryPatient.save(updatedPatient);
                });
    }

    // 🔹 DELETE: supprimer un patient
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        repositoryPatient.deleteById(id);
    }




    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        // Sauvegarde du patient dans la base de données
        Patient savedPatient = repositoryPatient.save(patient);

        // Envoi des données du patient au topic Kafka
        patientProducer.sendPatientData(savedPatient);

        return savedPatient;
    }
}
