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

    @Bean
    CommandLineRunner commandLineRunner(MedicalRecordRepository medicalRecordRepository,
                                        LabResultRepository labResultRepository,
                                        DoctorRestClient doctorRestClient,
                                        PatientRestClient patientRestClient) {
        List<Patient> patients = patientRestClient.getAllPatients();
        List<Doctor> doctors = doctorRestClient.getAllDoctors();
        return args -> {
            patients.forEach(patient -> {
                medicalRecordRepository.save(
                        MedicalRecord.builder()
                                .date(LocalDate.now())
                                .diagnosis("Flu")
                                .prescription("Rest and hydration")
                                .notes("Patient is recovering well.")
                                .patientId(patient.getId())
                                .doctorId(doctors.get(new Random().nextInt(doctors.size())).getId()) //
                                .build()
                );
            });
            List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
            medicalRecords.forEach(medicalRecord -> {
                labResultRepository.save(
                        LabResult.builder()
                                .testName("Blood Test")
                                .result("Normal")
                                .testDate(LocalDate.now())
                                .medicalRecord(medicalRecord)
                                .build()
                );
            });

//            medicalRecordRepository.save(
//                    MedicalRecord.builder()
//                            .date(LocalDate.now())
//                            .diagnosis("Headache")
//                            .prescription("Pain relievers")
//                            .notes("Patient advised to rest.")
//                            .build()
//            );
//            medicalRecordRepository.save(
//                    MedicalRecord.builder()
//                            .date(LocalDate.now())
//                            .diagnosis("Back pain")
//                            .prescription("Physical therapy")
//                            .notes("Patient to follow up in 2 weeks.")
//                            .build()
//            );
            medicalRecordRepository.findAll().forEach(m->{
                System.out.println("====================");
                System.out.println(m.getId());
                System.out.println(m.getDate());
                System.out.println(m.getDiagnosis());
                System.out.println(m.getPrescription());
                System.out.println(m.getNotes());
                m.getLabResults().forEach(labResult -> {
                    System.out.println("Lab Result: " + labResult.getTestName() + " - " + labResult.getResult());
                });
            });
        };
    }
}
