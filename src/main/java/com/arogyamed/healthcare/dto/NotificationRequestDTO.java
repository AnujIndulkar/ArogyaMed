package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.NotificationType;
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
