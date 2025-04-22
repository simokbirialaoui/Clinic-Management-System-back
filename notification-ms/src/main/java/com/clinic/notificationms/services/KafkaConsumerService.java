package com.clinic.notificationms.services;
import com.clinic.notificationms.controllers.ControllerNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private ControllerNotification notificationController;

    @KafkaListener(topics = "appointment-topic", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("Kafka message reçu: " + message);
        notificationController.sendNotification(message);
    }
}