package com.clinic.appointmentms;

import com.clinic.appointmentms.entities.Appointment;
import com.clinic.appointmentms.enums.Status;
import com.clinic.appointmentms.feign.DoctorRestClient;
import com.clinic.appointmentms.feign.PatientRestClient;
import com.clinic.appointmentms.model.Doctor;
import com.clinic.appointmentms.model.Patient;
import com.clinic.appointmentms.repository.AppointmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class AppointmentMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentMsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AppointmentRepository appointmentRepository, DoctorRestClient doctorRestClient, PatientRestClient patientRestClient) {
        return args -> {
            List<Patient> patients = patientRestClient.getAllPatients();
            List<Doctor> doctors = doctorRestClient.getAllDoctors();
            patients.forEach(patient -> {
                appointmentRepository.save(
                        Appointment.builder()
                                .appointmentDateTime(LocalDateTime.now())
                                .patientId(patient.getId())
                                .doctorId(doctors.get(new Random().nextInt(doctors.size())).getId())
                                .status(Status.values()[new Random().nextInt(Status.values().length)])
                                .build()
                );
            });

//
            appointmentRepository.findAll().forEach(c -> {
                System.out.println("====================");
                System.out.println(c.getId());
                System.out.println(c.getAppointmentDateTime());
                System.out.println(c.getStatus());
            });
        };
    }

}
