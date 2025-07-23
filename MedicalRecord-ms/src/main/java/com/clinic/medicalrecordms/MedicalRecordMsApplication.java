package com.clinic.medicalrecordms;

import com.clinic.medicalrecordms.entities.LabResult;
import com.clinic.medicalrecordms.entities.MedicalRecord;
import com.clinic.medicalrecordms.feign.DoctorRestClient;
import com.clinic.medicalrecordms.feign.PatientRestClient;
import com.clinic.medicalrecordms.model.Doctor;
import com.clinic.medicalrecordms.model.Patient;
import com.clinic.medicalrecordms.repository.LabResultRepository;
import com.clinic.medicalrecordms.repository.MedicalRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class MedicalRecordMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalRecordMsApplication.class, args);
    }
}
