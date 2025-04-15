package com.clinic.doctorms.repositories;

import com.clinic.doctorms.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDoctor extends JpaRepository<Doctor,Long> {
}
