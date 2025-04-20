package com.clinic.notificationms.controllers;

import com.clinic.notificationms.entities.Notification;
import com.clinic.notificationms.repositories.RepositoryNotification;
import com.clinic.notificationms.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private EmailService emailService;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = repository.findById(id);
        return notification.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
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

        return savedNotification;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification updatedNotif) {
        return repository.findById(id).map(notification -> {
            notification.setMessage(updatedNotif.getMessage());
            notification.setType(updatedNotif.getType());
            notification.setRecipientId(updatedNotif.getRecipientId());
            notification.setRecipientType(updatedNotif.getRecipientType());
            notification.setRecipientEmail(updatedNotif.getRecipientEmail());
            notification.setRead(updatedNotif.isRead());
            repository.save(notification);
            return ResponseEntity.ok(notification);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        return repository.findById(id).map(notification -> {
            notification.setRead(true);
            repository.save(notification);
            return ResponseEntity.ok(notification);
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

}