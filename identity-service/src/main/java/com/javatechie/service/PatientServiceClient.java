package com.javatechie.service;

import com.javatechie.dto.PatientRequest;
import com.javatechie.entity.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public Long createPatient(UserCredential user) {
        PatientRequest patient = new PatientRequest();
        patient.setFirstName(user.getFirstName());
        patient.setLastName(user.getLastName());
        patient.setEmail(user.getEmail());
        patient.setPhone(user.getPhone());

        // Appel vers patient-ms (modifie l’URL si tu utilises Eureka/Gateway)
        ResponseEntity<Long> response = restTemplate.postForEntity(
                "http://localhost:8082/api/patients",  // ou nom du service Eureka : "http://patient-ms/api/patients"
                patient,
                Long.class
        );

        return response.getBody();
    }
}

