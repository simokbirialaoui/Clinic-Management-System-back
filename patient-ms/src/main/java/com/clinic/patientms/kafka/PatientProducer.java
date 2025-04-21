package com.clinic.patientms.kafka;

import com.clinic.patientms.dtos.PatientCreatedEvent;
import com.clinic.patientms.entities.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PatientProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendPatientData(Patient patient) {
        try {
            String json = objectMapper.writeValueAsString(patient);
            kafkaTemplate.send("patient-topic", json);
            System.out.println("✅ Patient envoyé à Kafka : " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}