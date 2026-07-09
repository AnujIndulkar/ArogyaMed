package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.NotificationRequestDTO;
import com.arogyamed.healthcare.dto.NotificationResponseDTO;
import com.arogyamed.healthcare.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
