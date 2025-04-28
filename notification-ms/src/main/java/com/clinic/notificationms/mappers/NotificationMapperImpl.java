package com.clinic.notificationms.mappers;

import com.clinic.notificationms.dto.NotificationDto;
import com.clinic.notificationms.entities.Notification;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationMapperImpl {

    public NotificationDto fromNotification(Notification notification) {
        if (notification == null) return null;
        NotificationDto dto = new NotificationDto();
        BeanUtils.copyProperties(notification, dto);
        return dto;
    }

    public Notification fromNotificationDto(NotificationDto dto) {
        if (dto == null) return null;
        Notification notification = new Notification();
        BeanUtils.copyProperties(dto, notification);
        return notification;
    }

    public List<NotificationDto> fromNotificationList(List<Notification> notifications) {
        return notifications.stream()
                .map(this::fromNotification)
                .collect(Collectors.toList());
    }

    public List<Notification> fromNotificationDtoList(List<NotificationDto> notificationDtos) {
        return notificationDtos.stream()
                .map(this::fromNotificationDto)
                .collect(Collectors.toList());
    }
}
