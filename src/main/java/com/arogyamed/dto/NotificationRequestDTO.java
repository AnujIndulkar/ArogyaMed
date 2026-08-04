package com.arogyamed.dto;

import com.arogyamed.model.NotificationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    private Long userId;

    private String title;

    private String message;

    private NotificationType type;

}
