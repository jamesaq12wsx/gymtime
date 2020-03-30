package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.jamesaq12wsx.gymtime.model.PostPrivacy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonSubTypes({ @JsonSubTypes.Type(SimpleExercisePost.class),
                @JsonSubTypes.Type(SimpleExercisePostAudit.class),
                @JsonSubTypes.Type(SimpleExercisePostWithClubInfo.class)})
public interface ExercisePost {

    UUID getUuid();

    LocalDateTime getPostTime();

    PostPrivacy getPrivacy();

    UUID getClubUuid();

    List<PostExercise> getExercises();

}
