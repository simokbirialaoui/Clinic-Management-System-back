package com.clinic.notificationms.entities;

import com.clinic.notificationms.enums.NotificationType;
import com.clinic.notificationms.enums.RecipientType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor  @NoArgsConstructor @Builder

@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime timestamp;

    private boolean isRead;
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Long recipientId;
    private String recipientEmail;
    // Pour différencier le type de destinataire si besoin
    @Enumerated(EnumType.STRING)
    private RecipientType recipientType;

}
