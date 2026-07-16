package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.ActionStatus;
import com.arogyamed.healthcare.model.ActionType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogRequestDTO {

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

}
