package com.clinic.patientms.repositories;
import com.clinic.patientms.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPatient extends JpaRepository<Patient,Long> {
}
