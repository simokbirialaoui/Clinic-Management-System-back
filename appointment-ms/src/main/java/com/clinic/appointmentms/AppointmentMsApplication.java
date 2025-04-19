package com.clinic.appointmentms;

import com.clinic.appointmentms.entities.Appointment;
import com.clinic.appointmentms.enums.Status;
import com.clinic.appointmentms.repository.AppointmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class AppointmentMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentMsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AppointmentRepository appointmentRepository) {
        return args -> {
            appointmentRepository.save(
                    Appointment.builder()
                            .appointmentDateTime(LocalDateTime.now())
                            .status(Status.BOOKED)
                            .build()
            );
            appointmentRepository.save(
                    Appointment.builder()
                            .appointmentDateTime(LocalDateTime.now())
                            .status(Status.COMPLETED)
                            .build()
            );
            appointmentRepository.save(
                    Appointment.builder()
                            .appointmentDateTime(LocalDateTime.now())
                            .status(Status.CANCELLED)
                            .build()
            );
            appointmentRepository.findAll().forEach(c->{
                System.out.println("====================");
                System.out.println(c.getId());
                System.out.println(c.getAppointmentDateTime());
                System.out.println(c.getStatus());
            });
        };
    }

}
