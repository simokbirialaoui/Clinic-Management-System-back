package com.clinic.doctorms;

import com.clinic.doctorms.entities.Doctor;
import com.clinic.doctorms.repositories.RepositoryDoctor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DoctorMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorMsApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(RepositoryDoctor repository) {
        return args -> {
            repository.save(new Doctor(null, "John", "Doe", "john.doe@example.com", "123456789", "Cardiology"));
            repository.save(new Doctor(null, "Jane", "Smith", "jane.smith@example.com", "987654321", "Dermatology"));
        };
    }

}
