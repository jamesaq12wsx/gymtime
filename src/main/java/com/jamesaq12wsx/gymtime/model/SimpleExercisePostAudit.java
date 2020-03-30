package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimpleExercisePostAudit extends SimpleExercisePost implements ExercisePost {

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public SimpleExercisePostAudit(UUID uuid, LocalDateTime postTime, PostPrivacy privacy, UUID clubUuid, List<PostExercise> exercises, String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(uuid, postTime, privacy, clubUuid, exercises);
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
