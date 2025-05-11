package com.clinic.medicalrecordms.repository;

import com.clinic.medicalrecordms.entities.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabResultRepository extends JpaRepository<LabResult, Long> {
}
