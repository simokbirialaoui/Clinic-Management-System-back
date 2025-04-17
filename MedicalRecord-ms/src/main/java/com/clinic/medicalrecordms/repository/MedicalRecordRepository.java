package com.clinic.medicalrecordms.repository;

import com.clinic.medicalrecordms.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

}
