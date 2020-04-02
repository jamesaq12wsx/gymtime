package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonDeserialize(as = SimpleExercisePost.class)
public interface ExercisePost {

    UUID getPostUuid();

    LocalDateTime getExerciseTime();

    PostPrivacy getPrivacy();

    UUID getClubUuid();

    List<? extends PostExercise> getExercises();

}
