package com.javatechie.service;

import com.javatechie.dto.DoctorRequest;
import com.javatechie.entity.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DoctorServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public Long createDoctor(UserCredential user) {
        DoctorRequest doctor = new DoctorRequest();
        doctor.setFirstName(user.getFirstName());
        doctor.setLastName(user.getLastName());
        doctor.setEmail(user.getEmail());
        doctor.setPhone(user.getPhone());
        doctor.setSpecialization("Généraliste"); // Valeur par défaut (à adapter)
        doctor.setAvailableDays(List.of());       // Vide ou à configurer ailleurs
        doctor.setStartTime(null);                // À définir selon ton besoin
        doctor.setEndTime(null);

        // Appel vers doctor-ms (URL à adapter si tu utilises Eureka / Gateway)
        ResponseEntity<Long> response = restTemplate.postForEntity(
                "http://localhost:8081/api/doctors",  // ou "http://doctor-ms/api/doctors" via Eureka
                doctor,
                Long.class
        );

        return response.getBody();
    }
}
