package com.arogyamed.healthcare.dto;

import com.arogyamed.healthcare.model.ActionStatus;
import com.arogyamed.healthcare.model.ActionType;
import com.arogyamed.healthcare.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogRequestDTO {

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

    private String browser;

    private String operatingSystem;

    private Integer responseStatus;

    private Boolean success;

}
