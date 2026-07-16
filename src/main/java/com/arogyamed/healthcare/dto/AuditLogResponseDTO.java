package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.ActionStatus;
import com.arogyamed.healthcare.model.ActionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponseDTO {

    private Long id;

    private Long userId;

    private String userName;

    private String role;

    private String moduleName;

    private ActionType actionType;

    private ActionStatus actionStatus;

    private String actionDescription;

    private String entityName;

    private Long entityId;

    private String ipAddress;

    private String requestUrl;

    private String httpMethod;

    private String userAgent;

    private String remarks;

    private LocalDateTime actionTime;

}