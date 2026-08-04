package com.arogyamed.service;

import com.arogyamed.dto.NotificationRequestDTO;
import com.arogyamed.dto.NotificationResponseDTO;
import com.arogyamed.model.NotificationType;

import java.time.LocalDateTime;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO request);

    NotificationResponseDTO getNotificationById(Long id);

    List<NotificationResponseDTO> getAllNotifications();

    List<NotificationResponseDTO> getNotificationsByUser(Long userId);

    NotificationResponseDTO markAsRead(Long id);

    // ================= Search =================

    // Search by User ID
    List<NotificationResponseDTO> searchByUserId(Long userId);

    // Search by User Email
    List<NotificationResponseDTO> searchByUserEmail(String email);

    // Search by Notification Type
    List<NotificationResponseDTO> searchByType(NotificationType type);

    // Search by Read Status
    List<NotificationResponseDTO> searchByReadStatus(Boolean isRead);

    // Search by Created Date Range
    List<NotificationResponseDTO> searchByCreatedDate(LocalDateTime startDate, LocalDateTime endDate);

}
