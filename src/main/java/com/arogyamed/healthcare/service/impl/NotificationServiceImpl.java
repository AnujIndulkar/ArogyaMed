package com.arogyamed.healthcare.service.impl;

import com.arogyamed.healthcare.dto.NotificationRequestDTO;
import com.arogyamed.healthcare.dto.NotificationResponseDTO;
import com.arogyamed.healthcare.model.Notification;
import com.arogyamed.healthcare.model.User;
import com.arogyamed.healthcare.repository.NotificationRepository;
import com.arogyamed.healthcare.repository.UserRepository;
import com.arogyamed.healthcare.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arogyamed.healthcare.model.NotificationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
                        new RuntimeException("User not found"));

        Notification notification = new Notification();

        notification.setUser(user);

        notification.setTitle(request.getTitle());

        notification.setMessage(request.getMessage());

        notification.setType(request.getType());

        // Default unread
        notification.setIsRead(false);

        // Current date & time
        notification.setCreatedAt(LocalDateTime.now());

        Notification savedNotification = notificationRepository.save(notification);

        return mapToDTO(savedNotification);
    }

    @Override
    public NotificationResponseDTO getNotificationById(Long id) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        return mapToDTO(notification);
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new RuntimeException("User not found"));

        return notificationRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO markAsRead(Long id) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        notification.setIsRead(true);

        Notification updatedNotification = notificationRepository.save(notification);

        return mapToDTO(updatedNotification);
    }

    private NotificationResponseDTO mapToDTO(Notification notification) {

        NotificationResponseDTO dto = new NotificationResponseDTO();

        dto.setId(notification.getId());

        dto.setUserId(notification.getUser().getId());

        dto.setUserName(notification.getUser().getFullName());

        dto.setTitle(notification.getTitle());

        dto.setMessage(notification.getMessage());

        dto.setType(notification.getType());

        dto.setIsRead(notification.getIsRead());

        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }

    // ================= Search =================

    @Override
    public List<NotificationResponseDTO> searchByUserId(Long userId) {

        return mapToDTOList(notificationRepository.findByUserId(userId));
    }

    @Override
    public List<NotificationResponseDTO> searchByUserEmail(String email) {

        return mapToDTOList(notificationRepository.findByUser_EmailContainingIgnoreCase(email));
    }

    @Override
    public List<NotificationResponseDTO> searchByType(NotificationType type) {

        return mapToDTOList(notificationRepository.findByType(type));
    }

    @Override
    public List<NotificationResponseDTO> searchByReadStatus(Boolean isRead) {

        return mapToDTOList(notificationRepository.findByIsRead(isRead));
    }

    @Override
    public List<NotificationResponseDTO> searchByCreatedDate(LocalDateTime startDate, LocalDateTime endDate) {

        return mapToDTOList(notificationRepository.findByCreatedAtBetween(startDate, endDate));
    }

    private List<NotificationResponseDTO> mapToDTOList(List<Notification> notifications) {

        return notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
