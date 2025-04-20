package com.clinic.notificationms;

import com.clinic.notificationms.entities.Notification;
import com.clinic.notificationms.enums.NotificationType;
import com.clinic.notificationms.enums.RecipientType;
import com.clinic.notificationms.repositories.RepositoryNotification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class NotificationMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationMsApplication.class, args);
    }
    @Bean
    CommandLineRunner initNotifications(RepositoryNotification repository) {
        return args -> {
            repository.save(Notification.builder()
                    .message("Votre rendez-vous est confirmé pour demain à 10h.")
                    .timestamp(LocalDateTime.now())
                    .isRead(false)
                    .type(NotificationType.APPOINTMENT_REMINDER)
                    .recipientId(1L)
                    .recipientType(RecipientType.PATIENT)
                    .build());

            repository.save(Notification.builder()
                    .message("Nouvelle ordonnance disponible.")
                    .timestamp(LocalDateTime.now())
                    .isRead(false)
                    .type(NotificationType.PRESCRIPTION_REMINDER)
                    .recipientId(2L)
                    .recipientType(RecipientType.PATIENT)
                    .build());

            repository.save(Notification.builder()
                    .message("Résultat de laboratoire disponible.")
                    .timestamp(LocalDateTime.now())
                    .isRead(false)
                    .type(NotificationType.LAB_RESULT_READY)
                    .recipientId(3L)
                    .recipientType(RecipientType.DOCTOR)
                    .recipientEmail("hananelahssini6@gmail.com")
                    .build());
        };
    }
}
