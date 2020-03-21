package com.jamesaq12wsx.gymtime.database;

import com.jamesaq12wsx.gymtime.model.ExercisePost;
import com.jamesaq12wsx.gymtime.model.PostCount;
import com.jamesaq12wsx.gymtime.model.payload.ClubPostHourCount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExercisePostDao extends Dao<ExercisePost> {

    List<ExercisePost> getAllMarksByUser(String username);

    List<PostCount> getGymHourPost(UUID clubUuid, LocalDate date);

}
