package com.jamesaq12wsx.gymtime.model;

import java.time.LocalDateTime;

public interface ExerciseAudit extends Exercise {

    String getCreatedBy();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

}
