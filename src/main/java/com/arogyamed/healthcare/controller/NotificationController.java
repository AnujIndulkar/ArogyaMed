package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.NotificationRequestDTO;
import com.arogyamed.healthcare.dto.NotificationResponseDTO;
import com.arogyamed.healthcare.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arogyamed.healthcare.model.NotificationType;
import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public NotificationResponseDTO createNotification(@RequestBody NotificationRequestDTO request) {
        return notificationService.createNotification(request);
    }

    @GetMapping("/{id}")
    public NotificationResponseDTO getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping
    public List<NotificationResponseDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponseDTO> getNotificationsByUser(@PathVariable Long userId) {
        return notificationService.getNotificationsByUser(userId);
    }

    @PutMapping("/{id}/read")
    public NotificationResponseDTO markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }

    // ================= Search =================

    // Search by User ID
    @GetMapping("/search/user")
    public List<NotificationResponseDTO> searchByUserId(@RequestParam Long userId) {
        return notificationService.searchByUserId(userId);
    }

    // Search by User Email
    @GetMapping("/search/email")
    public List<NotificationResponseDTO> searchByUserEmail(@RequestParam String email) {
        return notificationService.searchByUserEmail(email);
    }

    // Search by Notification Type
    @GetMapping("/search/type")
    public List<NotificationResponseDTO> searchByType(@RequestParam NotificationType type) {
        return notificationService.searchByType(type);
    }

    // Search by Read Status
    @GetMapping("/search/read-status")
    public List<NotificationResponseDTO> searchByReadStatus(@RequestParam Boolean isRead) {
        return notificationService.searchByReadStatus(isRead);
    }

    // Search by Created Date Range
    @GetMapping("/search/date")
    public List<NotificationResponseDTO> searchByCreatedDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {

        return notificationService.searchByCreatedDate(startDate, endDate);
    }

}
