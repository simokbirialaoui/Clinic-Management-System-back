package com.clinic.doctorms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DoctorMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorMsApplication.class, args);
    }
}
/*
    @Bean
    CommandLineRunner initDatabase(RepositoryDoctor repository) {
        return args -> {
            repository.save(Doctor.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .phone("123456789")
                    .specialization("Cardiology")
                    .availableDays(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))
                    .startTime(LocalTime.of(9, 0))
                    .endTime(LocalTime.of(17, 0))
                    .build());

            repository.save(Doctor.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .email("jane.smith@example.com")
                    .phone("987654321")
                    .specialization("Dermatology")
                    .availableDays(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY))
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(16, 0))
                    .build());
        };
    }

}

 */
