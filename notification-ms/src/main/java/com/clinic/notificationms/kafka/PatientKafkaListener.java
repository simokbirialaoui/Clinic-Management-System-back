package com.clinic.notificationms.kafka;
import com.clinic.notificationms.dto.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PatientKafkaListener {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "patient-topic", groupId = "doctor-group")
    public void consume(String message) {
        try {
            Patient patient = objectMapper.readValue(message, Patient.class);
            System.out.println("✅ Nouveau patient reçu : " + patient.getFirstName() + " " + patient.getLastName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
