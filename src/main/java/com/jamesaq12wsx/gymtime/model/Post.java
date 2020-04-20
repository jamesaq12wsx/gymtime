package com.jamesaq12wsx.gymtime.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jamesaq12wsx.gymtime.model.entity.ApplicationUser;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JsonDeserialize(as = SimplePost.class)
public interface Post {

    UUID getPostUuid();

    OffsetDateTime getExerciseTime();

    PostPrivacy getPrivacy();

    FitnessClub getClub();

    ApplicationUser getUser();

//    List<? extends PostExercise> getExercises();

}
