package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public interface AuditData {

    String getCreatedBy();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

}
