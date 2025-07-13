package com.javatechie.service;

import com.javatechie.dto.PatientRequest;
import com.javatechie.entity.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SystemTokenProvider systemTokenProvider;

    public Long createPatient(UserCredential user) {
        PatientRequest patient = new PatientRequest();
        patient.setFirstName(user.getFirstName());
        patient.setLastName(user.getLastName());
        patient.setEmail(user.getEmail());
        patient.setPhone(user.getPhone());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(systemTokenProvider.getSystemToken());

        HttpEntity<PatientRequest> entity = new HttpEntity<>(patient, headers);

        ResponseEntity<Long> response = restTemplate.postForEntity(
                "http://localhost:8082/api/patients",  // ou "http://patient-ms/api/patients" si Eureka/Gateway
                entity,
                Long.class
        );

        return response.getBody();
    }

    public void updatePatient(Long patientId, UserCredential user) {
        PatientRequest patient = new PatientRequest();
        patient.setFirstName(user.getFirstName());
        patient.setLastName(user.getLastName());
        patient.setEmail(user.getEmail());
        patient.setPhone(user.getPhone());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(systemTokenProvider.getSystemToken());

        HttpEntity<PatientRequest> entity = new HttpEntity<>(patient, headers);

        restTemplate.exchange(
                "http://localhost:8082/api/patients/" + patientId,
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }
}
