package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.NotificationRequestDTO;
import com.arogyamed.healthcare.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO request);

    NotificationResponseDTO getNotificationById(Long id);

    List<NotificationResponseDTO> getAllNotifications();

    List<NotificationResponseDTO> getNotificationsByUser(Long userId);

    NotificationResponseDTO markAsRead(Long id);

}
