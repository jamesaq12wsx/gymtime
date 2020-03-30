package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleExerciseAudit extends SimpleExercise implements ExerciseAudit {

    /**
     * Username
     */
    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public SimpleExerciseAudit(int id, String name, String description, String category, List<String> images, String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, name, description, category, images);
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
