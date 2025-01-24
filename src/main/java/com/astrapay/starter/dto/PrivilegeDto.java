package com.astrapay.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto {
    private long id;
    private String code;
    private String name;
    private String domain;
    private String module;
    private String action;
    private int version;
    private LocalDateTime createdAt;
    private long createdBy;
    private LocalDateTime updatedAt;
    private long updatedBy;
    private boolean isDeleted;
}