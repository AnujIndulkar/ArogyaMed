package com.arogyamed.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionStatus actionStatus;

    private Long userId;

    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String moduleName;

    private String entityName;

    private Long entityId;

    private String ipAddress;

    private String requestUrl;

    private String httpMethod;

    private String userAgent;

    private String browser;

    private String operatingSystem;

    private Integer responseStatus;

    private Boolean success;

    @Column(length = 2000)
    private String actionDescription;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime actionTime;

    @PrePersist
    public void onCreate(){

        this.actionTime = LocalDateTime.now();

    }

}