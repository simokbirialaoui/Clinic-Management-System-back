package com.clinic.medicalrecordms;

import com.clinic.medicalrecordms.entities.MedicalRecord;
import com.clinic.medicalrecordms.repository.MedicalRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class MedicalRecordMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalRecordMsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MedicalRecordRepository medicalRecordRepository) {
        return args -> {
            medicalRecordRepository.save(
                    MedicalRecord.builder()
                            .date(LocalDate.now())
                            .diagnosis("Flu")
                            .prescription("Rest and hydration")
                            .notes("Patient is recovering well.")
                            .build()
            );
            medicalRecordRepository.save(
                    MedicalRecord.builder()
                            .date(LocalDate.now())
                            .diagnosis("Headache")
                            .prescription("Pain relievers")
                            .notes("Patient advised to rest.")
                            .build()
            );
            medicalRecordRepository.save(
                    MedicalRecord.builder()
                            .date(LocalDate.now())
                            .diagnosis("Back pain")
                            .prescription("Physical therapy")
                            .notes("Patient to follow up in 2 weeks.")
                            .build()
            );
            medicalRecordRepository.findAll().forEach(m->{
                System.out.println("====================");
                System.out.println(m.getId());
                System.out.println(m.getDate());
                System.out.println(m.getDiagnosis());
                System.out.println(m.getPrescription());
                System.out.println(m.getNotes());
            });
        };
    }
}
