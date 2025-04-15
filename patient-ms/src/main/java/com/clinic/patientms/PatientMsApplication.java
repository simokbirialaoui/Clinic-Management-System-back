package com.clinic.patientms;

import com.clinic.patientms.entities.Patient;
import com.clinic.patientms.enums.Gender;
import com.clinic.patientms.repositories.RepositoryPatient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMsApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(RepositoryPatient repositoryPatient) {
        return args -> {
            Stream.of(
                    new Object[]{"Mohammed","Kbiri" , Gender.MALE},
                    new Object[]{"Hanane", "Lahssini",Gender.FEMALE},
                    new Object[]{"Janat","Fathi", Gender.FEMALE}
            ).forEach(data -> {
                String firstName = (String) data[0];
                String lastName = (String) data[1];
                Gender gender = (Gender) data[2];


                Patient patient = Patient.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(firstName.toLowerCase() + "@example.com")
                        .phone("0666666666")
                        .dateOfBirth(new Date())
                        .gender(gender)
                        .build();

                repositoryPatient.save(patient);
                System.out.println("Inserted patient: " + firstName);
            });
        };
    }

}
