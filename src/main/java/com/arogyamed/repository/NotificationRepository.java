package com.arogyamed.repository;

import com.arogyamed.model.Notification;
import com.arogyamed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.arogyamed.model.NotificationType;
import java.time.LocalDateTime;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);

    // ================= Search =================

    // Search by User ID
    List<Notification> findByUserId(Long userId);

    // Search by User Email
    List<Notification> findByUser_EmailContainingIgnoreCase(String email);

    // Search by Notification Type
    List<Notification> findByType(NotificationType type);

    // Search by Read Status
    List<Notification> findByIsRead(Boolean isRead);

    // Search by Created Date Range
    List<Notification> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
