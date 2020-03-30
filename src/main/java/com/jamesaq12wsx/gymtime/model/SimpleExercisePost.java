package com.jamesaq12wsx.gymtime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleExercisePost implements ExercisePost {

    private UUID uuid;

    private LocalDateTime postTime;

    private PostPrivacy privacy;

    /**
     * may be club uuid or null
     */
    private UUID clubUuid;

    private List<PostExercise> exercises;

}
