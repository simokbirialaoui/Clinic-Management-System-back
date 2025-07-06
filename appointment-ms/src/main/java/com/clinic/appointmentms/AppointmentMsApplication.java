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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

                LocalDate startDate = LocalDate.of(2025, 5, 25);
                LocalDate endDate = LocalDate.of(2025, 5, 27);

                long start = startDate.toEpochDay();
                long end = endDate.toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(start, end + 1);
                LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

                // Random time between 8:00 AM and 6:00 PM
                int startHour = 8;
                int endHour = 12; // exclusive upper bound, so 17 means up to 5:59 PM
                int randomHour = ThreadLocalRandom.current().nextInt(startHour, endHour + 1);
                int randomMinute = ThreadLocalRandom.current().nextInt(0, 60); // any minute from 0 to 59

                LocalTime randomTime = LocalTime.of(randomHour, randomMinute);

                // Combine date and time
                LocalDateTime randomDateTime = LocalDateTime.of(randomDate, randomTime);


                appointmentRepository.save(
                        Appointment.builder()
                                .appointmentDateTime(randomDateTime)
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
