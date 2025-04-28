package com.clinic.notificationms.controllers;
import com.clinic.notificationms.dto.NotificationDto;
import com.clinic.notificationms.entities.Notification;
import com.clinic.notificationms.mappers.NotificationMapperImpl;
import com.clinic.notificationms.repositories.RepositoryNotification;
import com.clinic.notificationms.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class ControllerNotification {

    @Autowired
    private RepositoryNotification repository;

    @Autowired
    private NotificationMapperImpl notificationMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = repository.findAll();
        return notificationMapper.fromNotificationList(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = repository.findById(id);
        return notification.map(n -> ResponseEntity.ok(notificationMapper.fromNotification(n)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public NotificationDto createNotification(@RequestBody NotificationDto notificationDto) {
        Notification notification = notificationMapper.fromNotificationDto(notificationDto);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        Notification savedNotification = repository.save(notification);

        // Envoi de l'email si un email est renseigné
        if (savedNotification.getRecipientEmail() != null && !savedNotification.getRecipientEmail().isEmpty()) {
            emailService.sendEmail(
                    savedNotification.getRecipientEmail(),
                    "Notification de la clinique",
                    savedNotification.getMessage()
            );
        }

        return notificationMapper.fromNotification(savedNotification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable Long id, @RequestBody NotificationDto updatedNotifDto) {
        return repository.findById(id).map(notification -> {
            notification.setMessage(updatedNotifDto.getMessage());
            notification.setType(updatedNotifDto.getType());
            notification.setRecipientId(updatedNotifDto.getRecipientId());
            notification.setRecipientType(updatedNotifDto.getRecipientType());
            notification.setRecipientEmail(updatedNotifDto.getRecipientEmail());
            notification.setRead(updatedNotifDto.isRead());
            Notification updatedNotification = repository.save(notification);
            return ResponseEntity.ok(notificationMapper.fromNotification(updatedNotification));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDto> markAsRead(@PathVariable Long id) {
        return repository.findById(id).map(notification -> {
            notification.setRead(true);
            Notification updatedNotification = repository.save(notification);
            return ResponseEntity.ok(notificationMapper.fromNotification(updatedNotification));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // WebSocket : envoyer une notification en temps réel
    public void sendNotification(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
