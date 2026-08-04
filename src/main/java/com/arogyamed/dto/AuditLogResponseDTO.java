package com.arogyamed.dto;

import com.arogyamed.model.ActionStatus;
import com.arogyamed.model.ActionType;
import com.arogyamed.model.Role;
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

    private Role role;

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

    private String browser;

    private String operatingSystem;

    private Integer responseStatus;

    private Boolean success;

}