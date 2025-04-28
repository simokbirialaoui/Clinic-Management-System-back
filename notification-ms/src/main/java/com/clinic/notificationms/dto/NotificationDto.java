package com.clinic.notificationms.dto;
import com.clinic.notificationms.enums.NotificationType;
import com.clinic.notificationms.enums.RecipientType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private Long id;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;
    private NotificationType type;
    private Long recipientId;
    private String recipientEmail;
    private RecipientType recipientType;
}
