package com.clinic.notificationms.repositories;

import com.clinic.notificationms.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryNotification extends JpaRepository<Notification,Long> {
}
