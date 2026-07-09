package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private Long id;

    private Long userId;

    private String userName;

    private String title;

    private String message;

    private NotificationType type;

    private Boolean isRead;

    private LocalDateTime createdAt;

}
