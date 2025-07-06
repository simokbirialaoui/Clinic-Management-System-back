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



}
